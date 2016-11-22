package com.google.drive.mock;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.admin.directory.model.Groups;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.drive.Drive.Children.List;
import com.google.api.services.drive.model.File;

public class GoogleDriveImpl implements GoogleDriveWrapper {
  private static final String MY_CUSTOMER = "my_customer";
  private GoogleCredential googleCredential;
  public GoogleDriveImpl(GoogleCredential googleCredential) {
    this.googleCredential = googleCredential;
  }

  @SuppressWarnings("serial")
  private Set<String> fileFieldsToFetch = new HashSet<String>() {
    {
      add("id");
      add("createdDate");
      add("modifiedDate");
      add("fileSize");
      add("ownerNames");
      add("title");
      add("description");
      add("mimeType");
      add("exportLinks");
      add("downloadUrl");
      add("permissions");
    }
  };
  
  public Set<String> getFileFieldsToFetch() {
    return fileFieldsToFetch;
  }

  public void setFileFieldsToFetch(Set<String> fileFieldsToFetch) {
    this.fileFieldsToFetch = fileFieldsToFetch;
  }

  @Override
  public File getFileById(String fileId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List getChildren(String filePath) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public InputStream getFileInputStream(String fileUrl) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Groups getUserGroups(String domain, String userNameWithoutDomain) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Users listUsers() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Users listUsers(String nextPageToken) {
    // TODO Auto-generated method stub
    return null;
  }

}
