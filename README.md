Simple Twitter Client
=============

This is an Android application to view a Twitter timeline and composing a new tweet.

**_Completed user stories:_**

#####Basic

- [x] Required: User can sign in to Twitter using OAuth login
- [x] Required: User can view the tweets from their home timeline
	* 	User should be displayed the username, name, and body for each tweet
	* 	User should be displayed the relative timestamp for each tweet "8m", "7h"
	* 	User can view more tweets as they scroll with infinite pagination
- [x] Required: User can compose a new tweet
	* 	User can click a “Compose” icon in the Action Bar on the top right
	* 	User can then enter a new tweet and post this to twitter
	* 	User is taken back to home timeline with new tweet visible in timeline

#####Advanced

- [x] Advanced: While composing a tweet, user can see a character counter with characters remaining for tweet out of 140
- [x] Advanced: Advanced: Links in tweets are clickable and will launch the web browser (see autolink)
- [x] Advanced: Advanced: User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh)
- [x] Advanced: Advanced: User can open the twitter app offline and see last loaded tweets
	* 	Tweets are persisted into sqlite and can be displayed from the local DB
- [x] Advanced: Advanced: User can tap a tweet to display a "detailed" view of that tweet
- [x] Advanced: Advanced: User can select "reply" from detail view to respond to a tweet
- [x] Advanced: Advanced: Improve the user interface and theme the app to feel "twitter branded"

**_Working in progress:_**
#####Bonus

- [ ] Bonus: User can see embedded image media within the tweet detail view
- [ ] Bonus: Compose activity is replaced with a modal overlay


**_API or Library used:_**

- Async HTTP (http://loopj.com/android-async-http/)
- ActiveAndroid (https://github.com/pardom/ActiveAndroid)
- ButterKnife (http://jakewharton.github.io/butterknife/)
- Picasso (http://square.github.io/picasso/)
- Material-Dialogs (https://github.com/afollestad/material-dialogs)


**_Screencast:_**

![screenshot](https://github.com/fengsterooni/mysimpletweets/blob/master/tweets.gif)

