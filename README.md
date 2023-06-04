
# Android app client - Chats App (DTWchats - Daniel and Tomer web chats)

## Important:
- **THIS APP IS INTENDED TO WORK WITH https://github.com/TomerPardi/WebAPI/tree/DB_Branch1 VERSION OF THE SERVER (there we have the DB implementation also, with the react app); PLEASE READ THE README THERE ALSO**
- We have a version that works with the Planet server under Planet_branch, and with server branch: https://github.com/TomerPardi/WebAPI/tree/Planet_branch you can see it in action below.


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#general">General</a>
        <li><a href="#logic">Logic</a></li>
    </li>
        <li><a href="#in-action-local">Demonstration local</a></li>
        <li><a href="#in-action-on-planet">Demonstration on planet</a></li>
        <li><a href="#requirements">Requirements</a></li>
    <li><a href="#technologies-used">Built With</a></li>
  </ol>
</details>

## General
- This is the third and last part out of three in the project for _Advanced Programming 2_ course at Bar Ilan University.
- In this part we implemented an android client for our chat app.
- We added an SQL database to the server from the 2nd part to manage the data better.
- The app uses firebase pushes to transmit the messages in real time.
- We also show notifications from the app when a new message is received(Also using Firebase).
- Authors: **Daniel Bronfman** & **Tomer Pardilov**

## Logic
We implemented **four main activities**:
- **Login**: The user is asked to insert username and password, both are required. There is an option to transfer to the registration page.
- **Registration**: The user is asked to insert username, nickname, password.
- **Contacts list**: Here we can see user's contacts list and the last messages from the contacts. There is a settings button, which allows the user to change the server address.
- **Chat**: Chat with a selected contact. Gets updated in real time when a message is sent.
The data is saved to the local SQLite storage, Room and are accessed vid DAO's. The data is regularily updated from the servers database using the REST API.


## In Action local
Registration and trying to register with existing user:

<img src="https://user-images.githubusercontent.com/72495653/174652685-67a32d2a-c0a5-4ac3-a99e-48e1b90633cf.gif"
     width="30%" height="30%"></img>

Registration and login


<img src="https://user-images.githubusercontent.com/72495653/174652921-3a806b5c-409a-481f-8f55-ad71516fcec0.gif"
     width="30%" height="30%"></img>

Here we can see the settings window and the logout button:



<img src="https://user-images.githubusercontent.com/72495653/174652990-90351acb-4756-4ebd-9bdd-092ce2d31bea.gif"
     width="30%" height="30%"></img>

Adding contact:

<img src="https://user-images.githubusercontent.com/72495653/174652990-90351acb-4756-4ebd-9bdd-092ce2d31bea.gif"
     width="30%" height="30%"></img>


Sending message to another android user shows a notification

<img src="https://user-images.githubusercontent.com/72495653/174653095-edee176e-df03-47c3-9116-a37497d53024.gif"
     width="80%" height="80%"></img>

and also shows notification and updates in chat


<img src="https://user-images.githubusercontent.com/72495653/174653144-f997e77f-8395-48a5-b4c5-5c3bdcfcf33f.gif"
     width="80%" height="80%"></img>


The react implementation is working with android:


<img src="https://user-images.githubusercontent.com/72495653/174653271-f1ac0866-d6c0-4ca0-bb24-cce185d76536.gif"
     width="90%" height="90%"></img>


And messages are being sent:


<img src="https://user-images.githubusercontent.com/72495653/174653306-5372df11-e973-459a-a5fb-dfaab86c1502.gif"
     width="90%" height="90%"></img>

## In Action on planet:

We created a local ssh tunnel:

![ssh](https://user-images.githubusercontent.com/72495653/175032691-8fdfe0d3-d155-4e6a-88ee-eb90abe7ef65.jpg)

And installed ngrok to run a remote tunnel:

![ngrok](https://user-images.githubusercontent.com/72495653/175032803-2b836e19-ef02-43a9-9a80-62b2f38f0a8a.jpg)

You change the server to the address of the ngrock proxy:

![change_server](https://user-images.githubusercontent.com/72495653/175032915-37e2b958-ad88-421b-86f0-006d157f3218.gif)

And you can add a user:

![add_user_remote](https://user-images.githubusercontent.com/72495653/175032959-ec2029dd-dbd3-4584-8f2a-773f947bddd9.gif)


And send and receive messages:

![message_remote](https://user-images.githubusercontent.com/72495653/175032990-d15c5f22-799f-460a-bae2-e848ea3218d7.gif)




## Requirements
Android minimum SDK 21, and target SDK 32

Gradle dependencies as seen in build.gradle:
    'com.squareup.okhttp3:okhttp:3.14.9'
    'com.squareup.retrofit2:retrofit:2.9.0'
    'com.squareup.retrofit2:converter-gson:2.5.0'
    'com.google.code.gson:gson:2.9.0'
    'androidx.appcompat:appcompat:1.4.2'
    'com.google.android.material:material:1.6.1'
    'androidx.constraintlayout:constraintlayout:2.1.4'
    'androidx.room:room-common:2.4.2'
    'androidx.room:room-runtime:2.4.2'
    platform('com.google.firebase:firebase-bom:30.1.0')
    'com.google.firebase:firebase-messaging'
    'com.google.firebase:firebase-core'
    'androidx.navigation:navigation-fragment:2.4.2'
    'androidx.navigation:navigation-ui:2.4.2'
    'junit:junit:4.13.2'
    'androidx.test.ext:junit:1.1.3'
    'androidx.test.espresso:espresso-core:3.4.0'
    'androidx.room:room-compiler:2.4.2'

## Technologies Used
- Android
- Firebase Cloud Messaging
- Entity Framework 6
- SQL
- MariaDB
- ngrok
