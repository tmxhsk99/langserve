package com.kjh.ollama.langserve.service;


import com.kjh.ollama.langserve.exception.GitLabServiceException;
import com.kjh.ollama.langserve.model.vo.ChangeFile;
import com.kjh.ollama.langserve.util.gitlab.PositionFactory;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GitLabService {

    private final GitLabApi gitLabApi;

    public GitLabService(@Value("${gitlab.api.url}") String gitLabUrl, @Value("${gitlab.api.token}") String gitLabToken) {
        this.gitLabApi = new GitLabApi(GitLabApi.ApiVersion.V4,gitLabUrl, gitLabToken);
    }

    /**
     * Merge Request의 변경된 파일의 목록을 가져온다.
     *
     * @param projectId
     * @param mergeRequestId
     * @return
     */
    public List<ChangeFile> getChangeFiles(Long projectId, Long mergeRequestId) {
        try {
            List<Diff> diffs = gitLabApi.getMergeRequestApi().getDiffs(projectId, mergeRequestId);
            return diffs.stream()
                    .map(diff -> new ChangeFile(diff.getNewPath(), diff.getOldPath(),diff.getNewFile()))
                    .collect(Collectors.toList());
        } catch (GitLabApiException e) {
            throw new GitLabServiceException(String.format("Error fetching changed files for MR %s in Project %s", mergeRequestId, projectId)
                    , e);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 특정 소스파일의 내용을 가져온다.
     *
     * @param projectId
     * @param filePath
     * @param ref
     * @return
     */
    public String getFileContent(Long projectId, String filePath, String ref) {
        try {
            return gitLabApi.getRepositoryFileApi().getFile(projectId, filePath, ref).getDecodedContentAsString();
        } catch (GitLabApiException e) {
            throw new GitLabServiceException(String.format("Error fetching file content for %s in project %s at ref %s", filePath, projectId, ref)
                    , e);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Merge Request에 지정된 줄 범위에 대한 리뷰 코멘트를 작성한다.
     */
    public Discussion postReviewComment(Long projectId, Long mergeRequestIid, String body,
                                        String filePath, int startLine, int endLine, String headSha,
                                        Boolean isNewFile) throws GitLabServiceException {
        try {
            Position position = PositionFactory.forLineRange(filePath, startLine, endLine, headSha, isNewFile);

            // Merge Request의 diff_refs 정보 가져오기
            MergeRequest mergeRequest = gitLabApi.getMergeRequestApi().getMergeRequest(projectId, mergeRequestIid);

            position.withBaseSha(mergeRequest.getDiffRefs().getBaseSha())
                    .withStartSha(mergeRequest.getDiffRefs().getStartSha());

            Discussion discussion = gitLabApi.getDiscussionsApi().createMergeRequestDiscussion(
                    projectId, mergeRequestIid, body, null, null, position);

            log.info("Posted review comment for MR {} in project {} on file {} for lines {}-{}",
                    mergeRequestIid, projectId, filePath, startLine, endLine);
            return discussion;
        } catch (GitLabApiException e) {
            log.error("Error posting review comment for MR {} in project {} on file {}",
                    mergeRequestIid, projectId, filePath, e);
            throw new GitLabServiceException("Failed to post review comment", e);
        }
    }



}
