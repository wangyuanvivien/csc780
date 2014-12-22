package edu.sfsu.easymemo.ocr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.sfsu.easymemo.R;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CaptureFragment extends Fragment {
	final static String fragmentAText = "fragmentAText";
	byte[] imageviewByteFromMainActivity;
	private Button saveTextandImage;
	private ImageView lastBitmap;
	private EditText ocrResultTextView;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.
		if (savedInstanceState != null) {

		}
		
	    Bundle args = getArguments();
	    if (args == null) {
	        Toast.makeText(getActivity(), "arguments is null " , Toast.LENGTH_LONG).show();
	    } else {
	        Toast.makeText(getActivity(), "text " + args , Toast.LENGTH_LONG).show();
	    }
		rootView = inflater.inflate(R.layout.capture_fragment, container, false);
		saveTextandImage = (Button) rootView.findViewById(R.id.saveTextandImage);
		saveTextandImage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				File fileText = createDirectoryAndFile("Text");
				File fileImage = createDirectoryAndFile("Image");
			/*	if (fileText != null && ocrResultTextView.getText().toString().getBytes() != null) {
					writeFileToDirectory(fileText, ocrResultTextView.getText().toString().getBytes());
					
					
				}
				if (fileImage != null && imageviewByteFromMainActivity != null) {
					writeFileToDirectory(fileImage, imageviewByteFromMainActivity);
				}*/
				
				if(ocrResultTextView.getText().toString().getBytes() != null){
					String [] splits = ocrResultTextView.getText().toString().split("\\|");

	                Intent i = new Intent(getActivity(), edu.sfsu.easymemo.ocr.AddActivity.class);
					i.putExtra("voice",splits[0]);
					i.putExtra("date", splits[1]);
					startActivity(i);
					
				}
				
			}
		});

		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// Inflate the layout for this fragment
		return rootView;
	}



	public void writeFileToDirectory(File file, byte[] data) {

		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public void updateImageViewAndText(byte[] byteArray, String s) {
		// set lastBitmap
		lastBitmap = (ImageView) getActivity().findViewById(R.id.lastBitmap);
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		lastBitmap.setImageBitmap(bitmap);

		// set ocrResultTextView
		ocrResultTextView = (EditText) getActivity().findViewById(R.id.ocrResultTextView);
		ocrResultTextView.setText(s);
		// int scaledSize = Math.max(22, 32 - s.length() / 4);
		// ocrResultTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, scaledSize);
		// ocrResultTextView.setTextColor(Color.BLACK);
	}

	// Save public files on the external storage
	public File createDirectoryAndFile(String type) {
		// Get the directory for the user's public pictures directory.
		File directory = new File(Environment.getExternalStorageDirectory(), "OCRCrimsonBits");
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File file;
		if (type.equals("Image")) {
			file = new File(Environment.getExternalStorageDirectory() + File.separator + "OCRCrimsonBits" + File.separator + timeStamp + ".jpg");
		} else if (type.equals("Text")) {
			file = new File(Environment.getExternalStorageDirectory() + File.separator + "OCRCrimsonBits" + File.separator + timeStamp + ".txt");
		} else {
			return null;
		}
		return file;
	}

	@Override
	public void onStart() {
		super.onStart();

		Bundle args = getArguments();
		if (args != null) {
			imageviewByteFromMainActivity = args.getByteArray("lastBitmap");
			// Set article based on argument passed in
			updateImageViewAndText(imageviewByteFromMainActivity, args.getString("ocrResultTextView")+ "|" +args.getString("date"));
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

}