package com.kjh.ollama.langserve.util.gitlab;

import org.gitlab4j.api.models.Position;

/**
 * GitLab 코드 리뷰를 위한 Position 객체를 생성하는 팩토리 클래스.
 * 다양한 코드 리뷰 상황에 맞는 Position 객체를 쉽게 생성할 수 있도록 도와줍니다.
 */
public class PositionFactory {

    /**
     * 새로 추가된 파일에 대한 Position 객체를 생성합니다.
     *
     * @param newPath 새 파일의 경로
     * @param newLine 코멘트를 달 라인 번호
     * @param headSha Merge Request의 최신 커밋 SHA
     * @return 새 파일에 대한 Position 객체
     */
    public static Position forNewFile(String newPath, int newLine, String headSha) {
        return new Position()
                .withNewPath(newPath)
                .withNewLine(newLine)
                .withPositionType(Position.PositionType.TEXT)
                .withHeadSha(headSha);
    }

    /**
     * 기존 파일의 변경된 부분에 대한 Position 객체를 생성합니다.
     *
     * @param filePath 변경된 파일의 경로
     * @param oldLine 변경 전 라인 번호 (삭제된 라인의 경우)
     * @param newLine 변경 후 라인 번호 (추가 또는 수정된 라인의 경우)
     * @param headSha Merge Request의 최신 커밋 SHA
     * @return 변경된 파일에 대한 Position 객체
     */
    public static Position forModifiedFile(String filePath, Integer oldLine, Integer newLine, String headSha) {
        return new Position()
                .withOldPath(filePath)
                .withNewPath(filePath)
                .withOldLine(oldLine)
                .withNewLine(newLine)
                .withPositionType(Position.PositionType.TEXT)
                .withHeadSha(headSha);
    }

    /**
     * 삭제된 파일에 대한 Position 객체를 생성합니다.
     *
     * @param oldPath 삭제된 파일의 원래 경로
     * @param oldLine 삭제된 라인의 번호
     * @param headSha Merge Request의 최신 커밋 SHA
     * @return 삭제된 파일에 대한 Position 객체
     */
    public static Position forDeletedFile(String oldPath, int oldLine, String headSha) {
        return new Position()
                .withOldPath(oldPath)
                .withOldLine(oldLine)
                .withPositionType(Position.PositionType.TEXT)
                .withHeadSha(headSha);
    }

    /**
     * 이름이 변경된 파일에 대한 Position 객체를 생성합니다.
     *
     * @param oldPath 변경 전 파일 경로
     * @param newPath 변경 후 파일 경로
     * @param newLine 코멘트를 달 라인 번호
     * @param headSha Merge Request의 최신 커밋 SHA
     * @return 이름이 변경된 파일에 대한 Position 객체
     */
    public static Position forRenamedFile(String oldPath, String newPath, int newLine, String headSha) {
        return new Position()
                .withOldPath(oldPath)
                .withNewPath(newPath)
                .withNewLine(newLine)
                .withPositionType(Position.PositionType.TEXT)
                .withHeadSha(headSha);
    }

    /**
     * 전체 Merge Request 컨텍스트를 포함한 Position 객체를 생성합니다.
     * 이 메서드는 모든 SHA 값을 명시적으로 설정해야 하는 경우 사용합니다.
     *
     * @param oldPath 변경 전 파일 경로
     * @param newPath 변경 후 파일 경로
     * @param oldLine 변경 전 라인 번호
     * @param newLine 변경 후 라인 번호
     * @param baseSha Merge Request의 base 커밋 SHA
     * @param startSha 비교 시작 커밋 SHA
     * @param headSha Merge Request의 최신 커밋 SHA
     * @return 전체 컨텍스트를 포함한 Position 객체
     */
    public static Position forFullContext(String oldPath, String newPath, Integer oldLine, Integer newLine,
                                          String baseSha, String startSha, String headSha) {
        return new Position()
                .withOldPath(oldPath)
                .withNewPath(newPath)
                .withOldLine(oldLine)
                .withNewLine(newLine)
                .withPositionType(Position.PositionType.TEXT)
                .withBaseSha(baseSha)
                .withStartSha(startSha)
                .withHeadSha(headSha);
    }

    /**
     * 파일의 지정된 줄 범위에 대한 Position 객체를 생성한다.
     * @param filePath 파일 경로
     * @param startLine 범위의 시작 줄 번호
     * @param endLine 범위의 끝 줄 번호
     * @param headSha Merge Request의 최신 커밋 SHA
     * @param isNewFile 새 파일 여부
     * @return 지정되 줄 범위에 대한 Positon 객체
     */
    public static Position forLineRange(String filePath, int startLine, int endLine, String headSha, boolean isNewFile){
        Position position = new Position()
                .withNewPath(filePath)
                .withNewLine(endLine) // 코멘트 할 마지막 줄
                .withPositionType(Position.PositionType.TEXT)
                .withHeadSha(headSha);

        if(!isNewFile){
            position.withOldPath(filePath)
                    .withOldLine(startLine); // 코멘트 시작 줄
        }

        return position;
    }
}