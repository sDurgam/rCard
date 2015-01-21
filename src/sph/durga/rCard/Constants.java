package sph.durga.rCard;

/**
 * Defines several constants used between {@link BluetoothChatService} and the UI.
 */
public interface Constants {
 
    // Message types sent from the BluetoothChatService Handler
   
    public static final String email_error = "please enter your email address";    
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
  
    public static final String jobseeker = "jobseeker";
    public static final String recruiter = "recruiter";
    public static final String companydetails = "companydetails";
    public static final String COMPANY_ID = "myrcard_company_id";
    
    public static final String jobseeker_email = "email";
    public static enum jobseeker_priority {low, medium, high};
    
    //bluetooth socket variables
    public static final String TOAST = "toast";
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_JSON_DATA_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_RCARD_JSON_DATA = 6;
    
    public static final String RCARD_JSON_DATA = "rcard_json_data";
    public static enum sockettype {client, server};
    public static final String message_rcards_received_saved = "successfully saved rCard received from ";
    public static final String message_rcards_received_notsaved = "Failed saving rCard received from ";
    public static final String message_mycard_save_success = "rCard successfully saved";
    public static final String message_mycard_save_failed = "rCard could not be saved";
    
}