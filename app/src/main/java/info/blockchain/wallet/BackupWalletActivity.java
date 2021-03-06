package info.blockchain.wallet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import info.blockchain.wallet.util.AppUtil;

import piuk.blockchain.android.R;

public class BackupWalletActivity extends ActionBarActivity {

    public static final String BACKUP_DATE_KEY = "BACKUP_DATE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_backup_wallet);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar_general);
        toolbar.setTitle(getResources().getString(R.string.backup_wallet));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new BackupWalletFragment1())
                .addToBackStack("backup_start")
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppUtil.getInstance(this).stopLockTimer();

        if (AppUtil.getInstance(this).isTimedOut() && !AppUtil.getInstance(this).isLocked()) {
            Intent i = new Intent(this, PinEntryActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() <= 1)
            finish();
        else
            getFragmentManager().popBackStack();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onPause() {
        AppUtil.getInstance(this).startLockTimer();
        super.onPause();
    }

    @Override
    public void onUserLeaveHint() {
        AppUtil.getInstance(this).setInBackground(true);
    }
}