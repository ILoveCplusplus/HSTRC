/*
 * IRremote: IRsendDemo - demonstrates sending IR codes with IRsend
 * An IR LED must be connected to Arduino PWM pin 3.
 * Version 0.1 July, 2009
 * Copyright 2009 Ken Shirriff
 * http://arcfn.com
 */


#include <IRremote.h>

IRsend irsend;

void setup()
{
  Serial.begin(9600);
  while (!Serial)
  {
    // do nothing
  } 
}

void loop() {
  char receiveVal;
  if(Serial.available() > 0)
  {
    
    receiveVal = Serial.read();
    switch(receiveVal)
    {
      //power on off
      case 'a':
      for (int i = 0; i < 3; i++) {
       irsend.sendSony(0xa90, 12);
       delay(40);
       }
      break;
      //volume down
      case 'b':
      for (int i = 0; i < 3; i++) {
       irsend.sendSony(0xc90, 12);
       delay(40);
       }
      break;
      //volume up
      case 'c':
      for (int i = 0; i < 3; i++) {
       irsend.sendSony(0x490, 12);
       delay(40);
       }
      break;
      //enter
      case 'd':
      for (int i = 0; i < 3; i++) {
       irsend.sendSony(0xd10, 12);
       delay(40);
       }
      break;
      //return
      case 'e':
      for (int i = 0; i < 3; i++) {
       irsend.sendSony(0xdd0, 12);
       delay(40);
       }
      break;
      //input
      case 'f':
      for (int i = 0; i < 3; i++) {
       irsend.sendSony(0xa50, 12);
       delay(40);
       }
      break;
      
    }
   // Serial.print(receiveVal);
    
}  
  
	
	delay(100); // 100 milli delay between each signal burst
}
