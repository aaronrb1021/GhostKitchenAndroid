# GhostKitchenAndroid
CSE248 Final Project Android App

YouTube presentation video link - https://www.youtube.com/watch?v=zc4y9-yo00A

This is the Android implementation of the Ghost Kitchen final project for CSE248 Spring 2020. 

The application was created in Android Studio. I utilized RetroFit for REST functionality. All network calls are handled asynchronously using the AsyncTask class. All AsyncTask objects are called in such a way as to prevent memory leaks. 

In this application, there are two different user roles. First, there are customers, that have basic roles you would expect for a customer. Next, there are store owners, which have privileged abilities for store creation and order management.

**Store Owner Features:**

1.Kitchen Creation – The store owner is able to manage all of their stores under a single account. The store owner simply signs up at the registration screen, then they are able to create and manage as many stores as they’d like. 

  - Stores have an image along with basic store information (phone number, address, etc.)

    - Store information can be edited as needed.

  - Stores have a custom food menu, separate to each kitchen. 

    - Items can be created as the owner wishes
    - Items can be edited as needed
    - Items can be deleted
    - Items are displayed neatly in categories and items are displayed alphabetically within categories. 
    - Existing categories are presented to the user as an option during the creation of new items (to prevent typos in future item entries)

  - Pending orders are displayed in the Kitchen listing to provide easy access to pending order information, as it is important the orders be completed quickly and not go unnoticed. 

2.Order Management – The owner has multiple lists of filtered orders they can view. The store owner also has full control over the completion or cancellation of items and can view all past orders. 

  - Filtering of orders (pending, all, completed, cancelled, etc.)

  - Management of orders (mark complete, mark pending, etc.) 

  - View order details such as order number, date, items ordered, and other order specifics. 

  - The store owner is presented with analytics of all kitchens, providing them with total sales, information on which kitchen is their best-performing, total orders, etc. 

**Customer Features:**

1.Shop – The user is presented with a bunch of different features for the shopping through different stores

  - Every store has a custom image to make shopping easier
  
  - Customers can search through all created stores with no filter
  
  - Customers have the option of clicking a “filter” button which allows them to search through all stores located a certain radius (in miles) from the selected zip code. I implemented this feature using the API located here - http://www.zipcodeapi.com/API
  
  - The star visible in each store page can be clicked to add the store to the customer’s “favorites” so that they can be easily viewed later without searching. 
  
    - Favorites can be added or removed easily by clicking the star
    
  - The user is presented with a neat, categorized menu with pricing when they click on any given store. 
  
  - Store information can be viewed by clicking the “Info” tab in the navigation bottom bar
  
2.Checkout – There are many options the user has during the checkout process
 
  - The user can click an item within the menu to add it to their cart. They can add any quantity they desire (anything below 50 units, as that’s the set maximum per item)
  
  - A custom bottom sheet component appears after an item is added to the cart, which tracks the user’s subtotal and number of items in the cart 
  
	  - The bottom sheet can be dragged up to be presented with the cart page
    
		  - Items can be added, removed, or modified from the cart in the bottom sheet, then the user can proceed to checkout when they are satisfied
      
  - The customer has the option of pickup or delivery for checkout
  
    - If the user selects delivery, they have to enter a user address
    - After the customer enters an address, it is saved to their account for future use. The customer can create and manage as many addresses as they would like. 
	  - If the user selects pickup then they simply provide a pickup name
    
  - The customer can pay for the order with either cash or credit
  
3.Order Management – The user can track and manage their orders

  - After an order is submitted, the customer is presented with an order ID
  
  - The user can travel to the “Orders” section of the app
  
  - Orders are presented in filtered lists of pending, completed, etc. 
  
  - The customer can click an order to provided with order details (items ordered, shipping address or pickup name, etc.) and the order status (pending, completed, etc). 

This about summarizes the functionality of the application in its current state. 
I think the main thing I’d like to have improved was a design philosophy of abstraction from the very beginning. When I first started creating the app, I focused heavily on designing classes which only worked for a single purpose. For example, the Kitchen List is something I reuse in various parts of the application, but I didn’t incorporate enough abstraction to easily implement this, which resulted in me using tactics such as public static final constants to implement modes in the list adapters, which gets messy and unmaintainable very quickly. However, later on in the app I started using abstraction heavily, such as for the Order Dialog classes, which allowed me to save the implementation until the later parts of the final use of the class, which provided modularity and reusability. One other thing is that I’d like to implement security features in the application.

