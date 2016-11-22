package com.google.drive.mock;

import java.io.InputStream;

import com.google.api.services.admin.directory.model.Groups;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.File;

public interface GoogleDriveWrapper {
  File getFileById(String fileId);
  ChildList getChildren(String fileId);
  InputStream getFileInputStream(String fileUrl);
  Groups getUserGroups(String domain, String userNameWithoutDomain);
  Users listUsers();
  Users listUsers(String nextPageToken);
}
