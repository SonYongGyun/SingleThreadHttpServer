package kr.co.mz.tutorial.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileCopierGuide {
    public static void main(String[] args) throws DirectoryCreationFailedException, TargetFileAlreadyExistsException, FileNotFoundException, FileCopyFailedException {
        var startTime = System.nanoTime();
        if (args.length != 2) {
            System.out.println("Usage : java -cp . kr.co.mz.tutorial.io.FileCopier [sourceFilePath] [targetDirectoryPath] ");
            return;
        }
        final String sourceFile = args[0];
        final String targetDirectory = args[1];
        var copiedFile = copyTo(sourceFile, targetDirectory);
        var endTime = System.nanoTime();
        System.out.println("파일 복사에 성공하였습니다:" + copiedFile.getAbsolutePath() + ":: Elapsed Time = [" + (endTime - startTime) / 1_000_000 + "ms]");
    }

    /**
     * strSourceFile에 해당하는 파일을 strTargetDirectory 경로에 복사한다.
     *
     * @param strSourceFile      소스 파일에 대한 절대 경로
     * @param strTargetDirectory 대상 디렉토리에 대한 절대 경로
     * @return 복사된 대상 파일
     * @throws FileNotFoundException            소스 파일이 없는 경우 발생한다
     * @throws DirectoryCreationFailedException 대상 디렉토리를 생성할 수 없을 때 발생한다.
     * @throws TargetFileAlreadyExistsException 대상 디렉토리에 소스 파일과 동일한 이름의 파일이 존재할 때 발생한다.
     * @throws FileCopyFailedException          파일 복사가 실패했을 때 발생한다.
     */
    private static File copyTo(String strSourceFile, String strTargetDirectory) throws FileNotFoundException, DirectoryCreationFailedException, TargetFileAlreadyExistsException, FileCopyFailedException {
        // input validation
        var sourceFile = new File(strSourceFile);
        if (!sourceFile.exists()) {
            throw new FileNotFoundException("Source file does not exist: " + strSourceFile);
        }
        var targetDirectory = new File(strTargetDirectory);
        if (!targetDirectory.exists()) {
            var mkdirResult = targetDirectory.mkdirs();
            if (!mkdirResult) {
                throw new DirectoryCreationFailedException("Failed to create directory:" + targetDirectory.getAbsolutePath());
            }
        }
        var targetFile = new File(targetDirectory, sourceFile.getName());
        if (targetFile.exists()) {
            throw new TargetFileAlreadyExistsException("Target File[" + targetFile.getAbsolutePath() + "] already exists.");
        }
        try (
                var sourceFileInputStream = new FileInputStream(sourceFile);
                var targetFileOutputStream = new FileOutputStream(targetFile)
        ) {
            var buffer = new byte[4096];
            int readCount = -1;
            while ((readCount = sourceFileInputStream.read(buffer)) != -1) {
                targetFileOutputStream.write(buffer, 0, readCount);
            }
//            var sourceFileLength = sourceFile.length(); // 파일 길이 받아오고 파일 길이만큼 반복하는것.
//            for (int i = 0; i < sourceFileLength; i++) { // 느리다.
//                targetFileOutputStream.write(sourceFileInputStream.read());
//            }
        } catch (Exception e) {
            throw new FileCopyFailedException("File Copy Failed: " + e.getMessage());
        }
        return targetFile;
    }
}