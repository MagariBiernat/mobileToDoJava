package com.example.todo.ui.Main_Page.Stats;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatsViewModel extends ViewModel {

    private MutableLiveData<String> mText;



    public StatsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }


    LiveData<String> getText() {
        return mText;
    }



}