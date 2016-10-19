package com.liquoratdoor.ladlite.exception;

import android.content.Context;

import com.liquoratdoor.ladlite.activity.R;


/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

  private ErrorMessageFactory() {
  }


  public static String create(Context context, ErrorBundle errorBundle) {
    String message;

    switch (errorBundle.getCode()){
      case 503 : message = context.getString(R.string.exception_message_no_connection);break;
      case 401: message = context.getString(R.string.exception_message_invalid_credential); break;
      case 403: message = context.getString(R.string.exception_message_invalid_credential); break;
      default:message = errorBundle.getMessage();//context.getString(R.string.exception_message_generic);

    }
  /*  if (exception instanceof NetworkConnectionException) {
      message = context.getString(R.string.exception_message_no_connection);
    } else if (exception instanceof DataNotFoundException) {
      message = context.getString(R.string.exception_message_data_not_found);
    }*/
    return message;
  }
}
