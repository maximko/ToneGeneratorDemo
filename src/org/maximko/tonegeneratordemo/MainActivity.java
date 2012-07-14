package org.maximko.tonegeneratordemo;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends Activity {
    
    final String TAG = "ToneGederatorDemo";
    
    ToneGenerator generator;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        generator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
        ListView listview = (ListView)findViewById(R.id.ListView);
        ArrayList<String> tones = new ArrayList<String>();
        Field[] tonefields = ToneGenerator.class.getDeclaredFields();
        for (Field field:tonefields) {
            if (field.getType() == Integer.TYPE) {
                tones.add(field.getName());
            }          
        }
        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tones));
        listview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                Object item = av.getItemAtPosition(i);
                try {
                    Field tone = ToneGenerator.class.getField(item.toString());
                    generator.startTone(tone.getInt(null));
                } catch (Exception ex) {
                    Log.e(TAG, ex.toString());
                }
            }         
        });
    }
    
    @Override
    public void onPause() {
        super.onPause();
        generator.release();
        finish();
    }
    
    public void Stop(View view) {
        generator.stopTone();
    }
    
}
