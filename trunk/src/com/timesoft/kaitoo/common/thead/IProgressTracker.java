package com.timesoft.kaitoo.common.thead;

import android.os.AsyncTask;

/**
 * Callback interface to monitor lifecycle of an {@link AsyncTask}
 * 
 * @author sorasaks
 */
public interface IProgressTracker {

    void onStartProgress();

    void onStopProgress();
}