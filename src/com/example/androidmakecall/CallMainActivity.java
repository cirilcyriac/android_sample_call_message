package com.example.androidmakecall;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CallMainActivity extends Activity {

	 private static int RESULT_LOAD_IMAGE = 1;
	    /** Called when the activity is first created. */
	protected static final int PICK_CONTACT = 0;
	private Button button1,Button2,button3,button4,imagebtn;
	private EditText  editText1,EditText2,editText3;
 ImageView image;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_main);
        
        
        
        button1 = (Button) findViewById(R.id.button1);
		editText1=(EditText)findViewById(R.id.editText1);
		 Button2 = (Button) findViewById(R.id.Button2);
			EditText2=(EditText)findViewById(R.id.EditText2);
			 button3 = (Button) findViewById(R.id.button3);
			 editText3=(EditText)findViewById(R.id.editText3);
			 button4 = (Button) findViewById(R.id.button4);
			  image=(ImageView)findViewById(R.id.imageView1);
			 imagebtn = (Button)findViewById(R.id.image);

		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// capture picture
				String no= editText1.getText().toString();
				
				 Intent i = new
					        Intent(android.content.Intent.ACTION_CALL,
					        Uri.parse("tel:+" + no));
					        startActivity(i);
			}
		});

		Button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// capture picture
				String no= editText1.getText().toString();
				String text= editText3.getText().toString();
				
				 /*Intent i = new
					        Intent(android.content.Intent.ACTION_CALL,
					        Uri.parse("tel:+" + no));
					        startActivity(i);*/
				System.out.println("NO:"+no.toString()+text.toString());
				 try {
						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage(no, null, text, null, null);
						
						System.out.println("NO:"+no.toString()+text.toString());
						
						Toast.makeText(getApplicationContext(), "SMS Sent!",
									Toast.LENGTH_LONG).show();
					  } catch (Exception e) {
						Toast.makeText(getApplicationContext(),
							"SMS faild, please try again later!",
							Toast.LENGTH_LONG).show();
						e.printStackTrace();
					  }
			}
		});
		
		
		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// capture picture
				 Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
	             startActivityForResult(intent, PICK_CONTACT);
			}
		});
		
		button4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// capture picture
				 
	             
	             Intent i = new Intent(Intent.ACTION_SEND);
	     		i.setData(Uri.parse("mailto:"));
	     		i.setType("text/plain");
	     		
	     		
	     		i.setType("message/rfc822");
	     		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{EditText2.getText().toString()});
	     		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
	     		i.putExtra(Intent.EXTRA_TEXT   , "body of email");
	     		try {
	     		    startActivity(Intent.createChooser(i, "Send mail..."));
	     		} catch (android.content.ActivityNotFoundException ex) {
	     		    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
	     		    
	     		}
	     			
			}
		});
		
//		 imagebtn.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View arg0) {
//	            	
//	            	Intent i=new Intent(CallMainActivity.this,SecondActivity.class);
//	            	startActivity(i);
//	                //Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//	               // startActivityForResult(i, RESULT_LOAD_IMAGE);
//	            }
//	        });
//       
       
    }
	
	void OpenNext()
	{
		
		System.out.println("hello");
		Intent i=new Intent(CallMainActivity.this,SecondActivity.class);
    	startActivity(i);
        //Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       // startActivityForResult(i, RESULT_LOAD_IMAGE);
	}
    
   
    @Override public void onActivityResult(int reqCode, int resultCode, Intent data){ super.onActivityResult(reqCode, resultCode, data);

    
    
   /* if (reqCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        System.out.println("index:"+columnIndex+picturePath.toString());
        cursor.close();
        image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        System.out.println("image"+(BitmapFactory.decodeFile(picturePath)));
    }
    */
    switch(reqCode)
    {
    
       case (PICK_CONTACT):
         if (resultCode == Activity.RESULT_OK)
         {
             Uri contactData = data.getData();
             Cursor c = managedQuery(contactData, null, null, null, null);
          if (c.moveToFirst())
          {
          String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

          String hasPhone =
          c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

          if (hasPhone.equalsIgnoreCase("1")) 
          {
         Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, 
          ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
         phones.moveToFirst();
         String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

         Cursor emailCur = getContentResolver().query(
                 ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                 null,
                 ContactsContract.CommonDataKinds.Email.CONTACT_ID
                         + " = ?", new String[] { id }, null);
          emailCur.moveToFirst();
            
            String cEmail = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
           Toast.makeText(getApplicationContext(), cNumber, Toast.LENGTH_SHORT).show();
           // setCn(cNumber);
           editText1.setText((cNumber));
           EditText2.setText((cEmail));
           
          }
         }
       }
    }
    }

    
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.call_main, menu);
        return true;
    }
    
    
    
}
