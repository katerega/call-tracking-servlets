# Call Tracking on Java Servlets

[![Build Status](https://travis-ci.org/TwilioDevEd/call-tracking-servlets.svg)](https://travis-ci.org/TwilioDevEd/call-tracking-servlets)

Use Twilio to track the effectiveness of your different marketing campaigns.
Learn how call tracking helps organizations in [these Twilio customer
stories](https://www.twilio.com/use-cases/call-tracking).

## Quickstart

### Create a TwiML App

This project is configured to use a **TwiML App**, which allows us to easily set
the voice URLs for all Twilio phone numbers we purchase in this app.

Create a new TwiML app at https://www.twilio.com/user/account/apps/add and use
its `Sid` as the `TWIML_APPLICATION_SID` environment variable wherever you run
this app.

![Creating a TwiML
App](http://howtodocs.s3.amazonaws.com/call-tracking-twiml-app.gif)

You can learn more about TwiML apps here:
https://www.twilio.com/help/faq/twilio-client/how-do-i-create-a-twiml-app

### Local development

This project was built using Java Servlets.

1. Clone this repository and `cd` into its directory:
 ```
 git clone git@github.com:TwilioDevEd/call-tracking-servlets.git
 ```

1. Create the database.

 ```bash
 $ createdb call-tracking-servlets

 ```

  _The application uses PostgreSQL as the persistence layer. If you
  don't have it already, you should install it. The easiest way is by
  using [Postgres.app](http://postgresapp.com/)._

1. Edit the sample configuration file `.environment` to match your database and Twilio's configuration:
     ```
  export DB_USERNAME=your_db_username
  export DB_PASSWORD=your_db_password
  export JDBC_URL=jdbc:postgresql://localhost:5432/call-tracking-servlets
  export TWILIO_ACCOUNT_SID=your_account_sid
  export TWILIO_AUTH_TOKEN=your_account_token
  export TWILIO_APP_SID=your_app_sid
     ```

  Once you have edited the `.environment` file, if you are using a UNIX operating system,
  just use the `source` command to load the variables into your environment:

  ```bash
  $ source .environment
  ```

  _If you are using a different operating system, make sure that all the
  variables from the `.environment` file are loaded into your environment._

1. Execute the migrations.
  ```bash
  $ ./gradlew flywayMigrate
  ```

1. Run the application.
  ```bash
  $ ./gradlew jettyRun
  ```

1. Check it out at [http://localhost:8080](http://localhost:8080)

    That's it!

Additionally, in order to let Twilio Phone numbers use the callback endpoint we exposed, our development server will need to be publicly accessible. [We recommend using ngrok to solve this problem](https://www.twilio.com/blog/2015/09/6-awesome-reasons-to-use-ngrok-when-testing-webhooks.html).

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
