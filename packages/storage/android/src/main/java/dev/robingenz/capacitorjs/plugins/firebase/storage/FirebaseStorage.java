package dev.robingenz.capacitorjs.plugins.firebase.storage;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.getcapacitor.JSArray;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FirebaseStorage {

    private final com.google.firebase.storage.FirebaseStorage storageInstance;

    public FirebaseStorage() {
        storageInstance = com.google.firebase.storage.FirebaseStorage.getInstance();
    }

    public StorageReference getReference(@Nullable String location) {
        return location == null ? storageInstance.getReference() : storageInstance.getReference(location);
    }

    public Task<Void> delete(@NonNull String location) {
        return getReference(location).delete();
    }

    /**
     * Loads a file in memory.
     * Max size of 1MB is allowed to prevent app crashes due to too large file.
     * @param location The location of the file which should be loaded in memory.
     * @return A task which contains the bytes of the file.
     */
    public Task<byte[]> getBytes(@NonNull String location) {
        return getReference(location).getBytes(1024 * 1024);
    }

    public FileDownloadTask getFile(@NonNull String location, @NonNull String name, @NonNull String extension) throws IOException {
        return getReference(location).getFile(File.createTempFile(name, extension));
    }

    public Task<Uri> getDownloadUrl(@NonNull String location) {
        return getReference(location).getDownloadUrl();
    }

    public UploadTask putBytes(@NonNull String location, @NonNull JSArray bytes) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(bytes);
        return getReference(location).putBytes(byteArrayOutputStream.toByteArray());
    }
}
