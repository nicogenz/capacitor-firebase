package dev.robingenz.capacitorjs.plugins.firebase.storage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;

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
}
