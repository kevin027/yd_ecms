package helper;

import helper.entity.GenTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Gen {
	
	static class GenForm {
		public String charset = "utf-8";
		public String baseRoot;
		public String sourceEntity;
		public String sourcePackage;
		public String destEntity;
		public String destPackage;
		public GenForm() {
			this.setSourceEntityCanonicalName(GenTemplate.class.getCanonicalName());
		}
		public void setSourceEntityCanonicalName(String sourceEntityCanonicalName) {
			sourceEntity = sourceEntityCanonicalName.replaceAll(".+?\\.", "");
			sourcePackage = sourceEntityCanonicalName.replaceAll(".entity." + sourceEntity, "");
		}
		public void setDestEntityCanonicalName(String destEntityCanonicalName) {
			destEntity = destEntityCanonicalName.replaceAll(".+?\\.", "");
			destPackage = destEntityCanonicalName.replaceAll(".entity." + destEntity, "");
		}
	}

	public static void main(String[] args) throws IOException {
		GenForm form = new GenForm();
		form.baseRoot = "E:/myeclipse-source/auditZh/src";
		form.setDestEntityCanonicalName("com.yida.basedata.audittype.entity.AuditType");
		gen(form);
	}
	
	public static void gen(final GenForm form) throws IOException {
		final Path root = Paths.get(form.baseRoot);
		final Path sourceDir = root.resolve(form.sourcePackage.replaceAll("\\.", "/"));
		final Path destDir = root.resolve(form.destPackage.replaceAll("\\.", "/"));
		final Charset charset = Charset.forName(form.charset);
		
		Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
			
			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				Objects.requireNonNull(dir);
				Objects.requireNonNull(attrs);
				if (dir.normalize().toString().contains(".svn")) {
					return FileVisitResult.SKIP_SUBTREE;
				}
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFile(Path soruce,
					BasicFileAttributes attrs) throws IOException {
				try {
					if (!soruce.getFileName().endsWith(Gen.class.getSimpleName() + ".java")) {
						String destFileName = soruce.getFileName().toString().replaceAll(form.sourceEntity, form.destEntity);
						Path dest = destDir.resolve(sourceDir.relativize(soruce)).resolveSibling(destFileName);
						
						if (!Files.exists(dest)) {
							List<String> lines = Files.readAllLines(soruce, charset);
							List<String> newLine = new ArrayList<String>(lines.size());
							String sourceEntity1 = form.sourceEntity.replaceFirst(form.sourceEntity.substring(0, 1), form.sourceEntity.substring(0, 1).toLowerCase());
							String destEntity1 = form.destEntity.replaceFirst(form.destEntity.substring(0, 1), form.destEntity.substring(0, 1).toLowerCase());
							for (int i = 0; i < lines.size(); i++) {
								newLine.add(lines.get(i)
										.replaceAll(sourceEntity1, destEntity1)
										.replaceAll(form.sourceEntity, form.destEntity)
										.replaceAll(form.sourcePackage, form.destPackage));
							}
							/*if (!Files.exists(dest.resolve("../"), LinkOption.NOFOLLOW_LINKS)) {
								Files.createFile(dest);
							}*/
							Path fileDir = dest.resolve("../").normalize();
							System.out.println(fileDir);
							if (!Files.exists(fileDir, LinkOption.NOFOLLOW_LINKS)) {
								Files.createDirectories(fileDir);
							}
							Files.write(dest, newLine, charset);
							System.out.println("create:" + dest);
						} else {
							throw new IllegalStateException("exists:" + dest.toString());
						}
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
				return FileVisitResult.CONTINUE;
			}
		}); 
	}
}
