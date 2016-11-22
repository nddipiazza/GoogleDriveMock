package com.google.drive.mock;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.WordUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.User;

public class GoogleDriveMockStore {
	private DB db = DBMaker.newMemoryDirectDB().transactionDisable().asyncWriteFlushDelay(100).make();
	private Map<String, User> users;
	private Map<String, Group> groups;
	private Map<String, String> userGroup;
	private Map<String, String> fileIdPath;
	private Map<String, String> pathToId;
	private Map<String, Permission> filePermissions;
	private Map<String, String> userFileShare;
	private Map<String, String> groupFileShare;
	private static String uuidToBase64(String str) {
	    UUID uuid = UUID.fromString(str);
	    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
	    bb.putLong(uuid.getMostSignificantBits());
	    bb.putLong(uuid.getLeastSignificantBits());
	    return Base64.encodeBase64URLSafeString(bb.array());
	}
	public GoogleDriveMockStore(InputStream usersCsv, InputStream groupsCsv, InputStream userGroupsCsv, String domain, String storeRootFilePath) throws IOException {
		users = db.getHashMap("users");
		try (InputStreamReader isr = new InputStreamReader(usersCsv); BufferedReader br = new BufferedReader(isr)) {
			String nextLine;
			while ((nextLine = br.readLine()) != null) {
				User newUser = new User();
				newUser.setDisplayName(WordUtils.capitalize(nextLine));
				newUser.setEmailAddress(nextLine + "@" + domain);
				newUser.setIsAuthenticatedUser(true);
				users.put(newUser.getEmailAddress(), newUser);
			}
		}
		groups = db.getHashMap("groups");
		try (InputStreamReader isr = new InputStreamReader(groupsCsv); BufferedReader br = new BufferedReader(isr)) {
			String nextLine;
			while ((nextLine = br.readLine()) != null) {
				Group newGroup = new Group();
				newGroup.setEmail(nextLine + "@" + domain);
				newGroup.setName(nextLine);
				newGroup.setDescription("Group " + nextLine);
				groups.put(newGroup.getEmail(), newGroup);
			}
		}
		userGroup = db.getHashMap("usergroups");
		try (InputStreamReader isr = new InputStreamReader(userGroupsCsv); BufferedReader br = new BufferedReader(isr)) {
			String nextLine;
			while ((nextLine = br.readLine()) != null) {
				String [] spl = nextLine.split(",");
				userGroup.put(spl[0], spl[1]);
			}
		}
		fileIdPath = db.getHashMap("fileidpath");
		pathToId = db.getHashMap("pathtoid");
		for (User user : users.values()) {
			String userHomePath = storeRootFilePath + File.separator + user.getEmailAddress().substring(0, user.getEmailAddress().indexOf("@"));
			fileIdPath.put(userHomePath, "root:" + user.getEmailAddress());
	        pathToId.put("root:" + user.getEmailAddress(), userHomePath);
			Path p = Paths.get(userHomePath);
		    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			        String fileName = file.getFileName().toString();
			        String uuid = UUID.randomUUID().toString();
			        String base64Version = uuidToBase64(uuid);
			        fileIdPath.put(fileName, base64Version);
			        pathToId.put(base64Version, fileName);
			        return FileVisitResult.CONTINUE;
				}
			};
		    try {
		      Files.walkFileTree(p, fv);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		}
		
	    userFileShare = db.getHashMap("userfileshare");
	    groupFileShare = db.getHashMap("groupfileshare");
	}
	public Map<String, User> getUsers() {
		return users;
	}
	public void setUsers(Map<String, User> users) {
		this.users = users;
	}
	public Map<String, Group> getGroups() {
		return groups;
	}
	public void setGroups(Map<String, Group> groups) {
		this.groups = groups;
	}
	public Map<String, String> getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(Map<String, String> userGroup) {
		this.userGroup = userGroup;
	}
	public Map<String, String> getFileIdPath() {
		return fileIdPath;
	}
	public void setFileIdPath(Map<String, String> fileIdPath) {
		this.fileIdPath = fileIdPath;
	}
	public Map<String, String> getPathToId() {
		return pathToId;
	}
	public void setPathToId(Map<String, String> pathToId) {
		this.pathToId = pathToId;
	}
	public Map<String, Permission> getFilePermissions() {
		return filePermissions;
	}
	public void setFilePermissions(Map<String, Permission> filePermissions) {
		this.filePermissions = filePermissions;
	}
	public Map<String, String> getUserFileShare() {
		return userFileShare;
	}
	public void setUserFileShare(Map<String, String> userFileShare) {
		this.userFileShare = userFileShare;
	}
	public Map<String, String> getGroupFileShare() {
		return groupFileShare;
	}
	public void setGroupFileShare(Map<String, String> groupFileShare) {
		this.groupFileShare = groupFileShare;
	}
	
}
