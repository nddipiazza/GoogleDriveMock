package com.google.drive.mock;

import java.io.InputStream;

import com.google.api.services.admin.directory.model.Groups;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.drive.Drive.Children.List;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;

public class MockGoogleDriveImpl implements GoogleDriveWrapper {

  private GoogleDriveMockStore googleDriveMockStore;

  public MockGoogleDriveImpl(GoogleDriveMockStore googleDriveMockStore) {
	  this.googleDriveMockStore = googleDriveMockStore;
  }

	@Override
	public File getFileById(String fileId) {
		if (!googleDriveMockStore.getFileIdPath().containsKey(fileId)) {

		}
		return null;
	}

  @Override
  public ChildList getChildren(String fileId) {
	  ChildList list = new ChildList();
	  if (googleDriveMockStore.getFileIdPath().containsKey(fileId)) {
		  String filePath = googleDriveMockStore.getFileIdPath().get(fileId);
		  java.io.File f = new java.io.File(filePath);
		  if (f.isDirectory()) {
			  for (java.io.File file : f.listFiles()) {
				  ChildReference ref = new ChildReference();
				  ref.setId(googleDriveMockStore.getPathToId().get(file.getAbsolutePath()));
				  list.getItems().add(ref);
			  }		  
		  }  
	  }
	  return list;
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
