package dev.robingenz.capacitorjs.plugins.firebase.storage;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FirebaseStorage")
public class FirebaseStoragePlugin extends Plugin {

    private static final String ERROR_FILE_LOCATION_MISSING = "file location must be provided.";
    private FirebaseStorage implementation;

    @Override
    public void load() {
        implementation = new FirebaseStorage();
    }

    @PluginMethod
    public void delete(PluginCall call) {
        String location = call.getString("location");
        if (location == null || location.trim().isEmpty()) {
            call.reject(ERROR_FILE_LOCATION_MISSING);
            return;
        }
        implementation.delete(location)
                .addOnSuccessListener(aVoid -> call.resolve())
                .addOnFailureListener(exception -> call.reject(exception.getMessage(), exception));
    }
}
