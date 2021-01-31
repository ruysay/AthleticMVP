# The Athletic Code Challange

The task of this code challange is to implement click events on the Articles in the ArticlesFragment RecyclerView so that:

-   Selecting an item in the Articles list opens a new, non-modal view (Activity/Fragment) using the same mvp architecture
-   The new view should contain:
	-    An ImageView displaying the content of the “imageUrlString” from the Article model at the top of the view
	-  A TextView displaying the article “title” underneath (or over) the ImageView
	- A TextView displaying all of the article “body” content underneath the Image/Title

  
# Implementation Note
1. This app uses existing MVP pattern, below components are created:
	- ArticleDetailActiviity:  [View] component to present UI of article image, title and body.
	- ArticleDetailContract:  This is an interface specifies the contract between ArticleDetailActiviity and ArticleDetailPresenter
	- ArticleDetailPresenter:  [Presenter] This class handles ArticleDetailActiviity UI updates based on changes to the data model
	- ArticleInteractor:  This class will handle interactions with Article
	- BaseActivity :  This class can be extended by Activities to leverage the MVP framework

2. Updated components are listed below:
	- BaseModule:  Enabled ArticleDetailPresenter
	- MainApplication:  Added initialisation of Picasso
	- ArticleRepository: Enabled functions to cache article list and get article by article ID
	- ArticleListAdapter: Link clicking article to ArticleDetailActivity

3. Unit test class: GetArticleDataSuccessTest is used to do initial function validation of Article
4. Mock web server is used in unit test cases to simulate response of the APIs to be integrated in the app.
5. There might be some unused components or code for presentation of an article with Fragment. After implementing article detail presentation with a Fragment, I think an stand alone Activity to present article or event would be a better idea because it can be used as a plugin for push notifications or more flexibilty to extend functions from the stand alone Activity.

# ToDos
Below are items I think can be improved in the future
1. Cache article list with Room DB
2. Updated layout of ArticleDetailActivity to provide better UX
3. UI test 

