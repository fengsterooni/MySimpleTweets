Simple Twitter Client
=============

This is an Android application for viewing a Twitter timeline and composing a new tweet.

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
	
- [x] Required: User can switch between Timeline and Mention views using tabs.
	* 	User can view their home timeline tweets.
	* 	User can view the recent mentions of their username.
- [x] Required: User can navigate to view their own profile
	* 	User can see picture, tagline, # of followers, # of following, and tweets on their profile.
- [x] Required: User can click on the profile image in any tweet to see another user's profile.
	* 	User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
	* 	Profile view should include that user's timeline
	* 	Optional: User can view following / followers list through the profile
- [x] Required: User can infinitely paginate any of these timelines (home, mentions, user) by scrolling to the bottom

#####Advanced

- [x] Advanced: While composing a tweet, user can see a character counter with characters remaining for tweet out of 140
- [x] Advanced: Advanced: Links in tweets are clickable and will launch the web browser (see autolink)
- [x] Advanced: Advanced: User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh)
- [x] Advanced: Advanced: User can open the twitter app offline and see last loaded tweets
	* 	Tweets are persisted into sqlite and can be displayed from the local DB
- [x] Advanced: User can tap a tweet to display a "detailed" view of that tweet
- [x] Advanced: User can select "reply" from detail view to respond to a tweet
- [x] Advanced: Improve the user interface and theme the app to feel "twitter branded"


- [x] Advanced: Robust error handling, check if internet is available, handle error cases, network failures
- [x] Advanced: When a network request is sent, user sees an indeterminate progress indicator
- [x] Advanced: User can "reply" to any tweet on their home timeline
The user that wrote the original tweet is automatically "@" replied in compose
- [x] Advanced: User can click on a tweet to be taken to a "detail view" of that tweet
- [x] Advanced: User can take favorite (and unfavorite) or reweet actions on a tweet
- [x] Advanced: Improve the user interface and theme the app to feel twitter branded
- [x] Advanced: User can search for tweets matching a particular query and see results

#####Bonus

- [X] Bonus: User can see embedded image media within the tweet detail view
- [X] Bonus: User can view their direct messages (or send new ones)

**_Working in progress:_**

#####Advanced
- [ ] Advanced: User can search for tweets matching a particular query and see results

#####Bonus

- [ ] Bonus: Compose activity is replaced with a modal overlay


**_API or Library used:_**

- Async HTTP (http://loopj.com/android-async-http/)
- ActiveAndroid (https://github.com/pardom/ActiveAndroid)
- ButterKnife (http://jakewharton.github.io/butterknife/)
- Picasso (http://square.github.io/picasso/)
- Material-Dialogs (https://github.com/afollestad/material-dialogs)
- Pager Sliding Tab Strip (https://github.com/astuetz/PagerSlidingTabStrip)
- Pretty Time (http://www.ocpsoft.org/prettytime/)


**_Screencast:_**

![screenshot](https://github.com/fengsterooni/mysimpletweets/blob/master/Tweets.gif)

