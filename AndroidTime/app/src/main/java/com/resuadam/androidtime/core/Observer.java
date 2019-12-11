package com.resuadam.androidtime.core;

/** Avoids direct dependency between model and view. */
public interface Observer {
    void setStatus(int strId);
    void setTimeInfo(DateTime data);
    void setDefaultValues();
}