<a href="https://www.twilio.com">
  <img src="https://static0.twilio.com/marketing/bundles/marketing/img/logos/wordmark-red.svg" alt="Twilio" width="250" />
</a>

# Call Tracking on Java Servlets

[![Build Status](https://travis-ci.org/TwilioDevEd/call-tracking-servlets.svg)](https://travis-ci.org/TwilioDevEd/call-tracking-servlets)

Use Twilio to track the effectiveness of your different marketing campaigns.
Learn how call tracking helps organizations in [these Twilio customer
stories](https://www.twilio.com/use-cases/call-tracking).

[Read the full tutorial here](https://www.twilio.com/docs/tutorials/walkthrough/call-tracking/java/servlets)!

## Quickstart

### Create a TwiML App

This project is configured to use a **TwiML App**, which allows us to easily set
the voice URLs for all Twilio phone numbers we purchase in this app.

Create a new TwiML app at https://www.twilio.com/user/account/apps/add and use
its `Sid` as the `TWIML_APPLICATION_SID` environment variable wherever you run
this app.

![Creating a TwiML App](http://howtodocs.s3.amazonaws.com/call-tracking-twiml-app.gif)

You can learn more about TwiML apps [here](https://www.twilio.com/help/faq/twilio-client/how-do-i-create-a-twiml-app).

### Local development

This project is build using [Java 8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html).

1. Clone this repository and `cd` into its directory:

   ```bash
   $ git clone git@github.com:TwilioDevEd/call-tracking-servlets.git
   $ cd call-tracking-servlets
   ```

1. Create the database.

   ```bash
   $ createdb call-tracking-servlets
   ```

   _The application uses PostgreSQL as the persistence layer. If you
   don't have it already, you should install it. The easiest way is by
   using [Postgres.app](http://postgresapp.com/)._

1. Copy the sample configuration file and edit it to match your configuration.

   ```bash
   $ cp .env.example .env
   ```

   You can find your `TWILIO_ACCOUNT_SID` and `TWILIO_AUTH_TOKEN` in your
   [Twilio Account Settings](https://www.twilio.com/user/account/settings).
   You will also need a `TWILIO_NUMBER`, which you may find [here](https://www.twilio.com/user/account/phone-numbers/incoming).

   Run `source .env` to export the environment variables.

1. Execute the migrations.

   ```bash
   $ ./gradlew flywayMigrate
   ```

1. Run the application.

   ```bash
   $ ./gradlew jettyRun
   ```

1. Check it out at [http://localhost:8080](http://localhost:8080)

Additionally, in order to let Twilio Phone numbers use the callback endpoint we
exposed, our development server will need to be publicly accessible.
[We recommend using ngrok to solve this problem](https://www.twilio.com/blog/2015/09/6-awesome-reasons-to-use-ngrok-when-testing-webhooks.html).

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
