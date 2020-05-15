package com.example.todo.ui.Main_Page.Notes;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main.Task;
import com.example.todo.ui.Main_Page.Main_page;

import java.util.ArrayList;

public class NotesFragment extends Fragment {


    private NotesViewModel notesViewModel;
    SQLiteDatabase myDatabase;
    private ListView myListView;
    LinearLayout layout;
    private ArrayList<Note> myNotes = new ArrayList<Note>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel =
                ViewModelProviders.of(this).get(NotesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        //get username from parent, set to VM

        String _username = ((Main_page)getActivity()).getUsername();
        notesViewModel.setUsername(_username);

        layout = root.findViewById(R.id.notesNone);
        //database
        myDatabase = ((Main_page)getActivity()).myDatabase;
        notesViewModel.setMyDatabase(myDatabase);

        myListView = root.findViewById(R.id.notesList);

        Button newNote = root.findViewById(R.id.buttonNewNote);

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main_page)getActivity()).newNoteActivity();
            }
        });


        notesViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<ArrayList<Note>>() {
            @Override
            public void onChanged(ArrayList<Note> strings) {
                myNotes.clear();
                myNotes = (ArrayList<Note>)strings.clone();
                fillTasks();
            }
        });

        return root;
    }

    private void fillTasks(){
        AdapterNotes adapterNotes;

        int count = myNotes.size();

        if(count == 0){
            layout.setVisibility(View.VISIBLE);
        } else{
            layout.setVisibility(View.GONE);
        }

        adapterNotes = new AdapterNotes(getActivity(),0,myNotes);

        myListView.setAdapter(adapterNotes);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((Main_page)getActivity()).showNoteActivity(myNotes.get(position).getId(), myNotes.get(position).getTitle());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        myNotes.clear();
        notesViewModel.setNotes();
    }

}
