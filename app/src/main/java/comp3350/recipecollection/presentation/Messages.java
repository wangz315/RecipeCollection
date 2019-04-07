package comp3350.recipecollection.presentation;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import comp3350.recipecollection.R;


public class Messages {
    public static final String MESSAGE_VIEW = "_VIEW_";
    public static final String MESSAGE_DETL = "_DETL_";
    public static final String DATABASE = "_DATABASE_";
    public static final String FAVO = "_FAVO_";
    public static final String MESSAGE_NEXT_BASIC = "_NEXT_BASIC_";
    public static final String MESSAGE_NEXT_STEPS = "_NEXT_STEPS_";
    public static final String MESSAGE_COMMENT = "_COMMENT_";
    public static final String UNKNOWN = "Unknown";
    public static final String NOIMAGE = "noimage";
    public static final String MESSAGE_EDIT_BASIC = "_EDIT_BASIC_";
    public static final String MESSAGE_EDIT_INGRE = "_EDIT_INGRE_";
    public static final String MESSAGE_EDIT_STEPS = "_EDIT_STEPS_";

    public static void fatalError(final Activity owner, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(owner).create();

        alertDialog.setTitle(owner.getString(R.string.fatalError));
        alertDialog.setMessage(message);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                owner.finish();
            }
        });

        alertDialog.show();
    }

    public static void warning(Activity owner, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(owner).create();

        alertDialog.setTitle(owner.getString(R.string.warning));
        alertDialog.setMessage(message);

        alertDialog.show();
    }
}
