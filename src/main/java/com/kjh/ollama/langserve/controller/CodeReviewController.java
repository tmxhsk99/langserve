package com.kjh.ollama.langserve.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjh.ollama.langserve.exception.GitLabServiceException;
import com.kjh.ollama.langserve.model.vo.ChangeFile;
import com.kjh.ollama.langserve.model.vo.GitLabWebhookEvent;
import com.kjh.ollama.langserve.model.vo.ReviewComment;
import com.kjh.ollama.langserve.service.AICodeReviewService;
import com.kjh.ollama.langserve.service.GitLabService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * gitLab으로 부터 온 이벤트를 처리한다.
 * WebHooks에 등록할 URL
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class CodeReviewController {

    private final GitLabService gitLabService;
    private final AICodeReviewService aiCodeReviewService;
    /**
     * 코드리뷰를 처리한다.
     */
    @PostMapping("/codeReview")
    public ResponseEntity<String> codeReview(@RequestBody GitLabWebhookEvent event) {
        log.info("Received GitLab webhook event: {}", event.eventType());

        if ("merge_request".equals(event.eventType())) {
            handleMergeRequestEvent(event);
        }
        return ResponseEntity.ok("Webhook Received");
    }

    private void handleMergeRequestEvent(GitLabWebhookEvent event) throws GitLabServiceException {
        Long projectId = event.project().getId();
        Long mergeRequestIid = event.objectAttributes().iid();
        String headSha = event.objectAttributes().lastCommit().getId();

        try {
            List<ChangeFile> changedFiles = gitLabService.getChangeFiles(projectId, mergeRequestIid);


            for (ChangeFile file : changedFiles) {
                String newFileContent = gitLabService.getFileContent(projectId, file.newPath(), event.objectAttributes().sourceBranch());
                String oldFileContent = gitLabService.getFileContent(projectId, file.oldPath(), event.objectAttributes().sourceBranch());


                List<ReviewComment> reviewComments = aiCodeReviewService.reviewCode(oldFileContent, newFileContent);

                for (ReviewComment reviewComment : reviewComments) {
                    int startLine = reviewComment.startLine(); // 새로운 메서드: 코멘트 시작 줄
                    int endLine = reviewComment.endLine();     // 새로운 메서드: 코멘트 끝 줄

                    // AI 서비스가 범위를 제공하지 않는 경우, 단일 라인을 범위로 취급
                    if (startLine == 0 && endLine == 0) {
                        startLine = reviewComment.lineNumber();
                        endLine = reviewComment.lineNumber();
                    }

                    gitLabService.postReviewComment(
                            projectId,
                            mergeRequestIid,
                            reviewComment.comment(),
                            file.newPath(),
                            startLine,
                            endLine,
                            headSha,
                            file.isNewFile()
                    );
                }
            }
        } catch (GitLabServiceException e) {
            log.error("Error processing merge request for project {} and MR {}", projectId, mergeRequestIid, e);
            throw e;
        }
    }


}