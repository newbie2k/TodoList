**Simple Android Todo List App**

**Purpose**

This Android project demonstrates fundamental Android app development concepts, focusing on task management functionality. It's great for beginners interested in learning:

* **UI Design:** Basic layout creation and views (Floating Action Button).
* **Fragments:** Using fragments for modular components within an activity.
* **Fragment Communication:** Implementing communication between fragments using interfaces.
* **Dialogs:** Creating custom dialogs for user input and interactions.
* **Data Persistence:** Using `SharedPreferences` for simple data storage. 

**Features**

* **Add Tasks:** Create new todo list items.
* **Add Subtasks:** Add subtasks to a main task.
* **Edit Tasks:** Modify existing task descriptions.
* **Delete Tasks:** Remove tasks using a swipe gesture.
* **Tablet Optimization:** Adapt the UI for tablet displays.
* **Welcome Tips:** Provide initial guidance for new users.

**How To Use**

1. **Import the project:** Import the project into Android Studio.
2. **Build and run:** Build and run the app on an Android device or emulator.

**Understanding the Code**

* **`MainActivity.java`:** 
   * Handles the main activity lifecycle.
   * Checks for tablet layout and adjusts UI accordingly.
   * Manages the Floating Action Button for adding tasks/subtasks.
   * Implements the `GetIdListener` interface for receiving task information from fragments.
   * Handles welcome tips using `SharedPreferences`.
 
* **`SubTaskActivity.java`:**
    * Handles the display of subtasks related to a selected main task.
    * Uses a Floating Action Button to trigger the `AddTaskDialog` for adding new subtasks.

* **`AddTaskDialog.java`:**
    * A custom dialog used for:
        * Adding new tasks
        * Adding new subtasks
        * Updating existing tasks 
        * Updating existing subtasks
    * Interacts with a `DialogTaskViewModel` (for data insertion, updates, and potentially retrieval).
    * Handles its own state restoration using `onSaveInstanceState`.

* **`SubTaskListFragment.java`:**
    * Displays a list of subtasks using a RecyclerView.
    * Observes `LiveData` from the `SubTaskViewModel` to update the list.
    * Implements the swipe-to-delete functionality using `ItemTouchHelper`.
    * Provides a callback (`onUpdateSubTaskLongPressListener`) for handling the update of subtasks.

* **`TaskListFragment.java`:**
    * Displays the list of main tasks using a RecyclerView.
    * Observes LiveData from the `TaskViewModel` to update the list.
    * Implements swipe-to-delete for tasks, including a confirmation dialog.
    * Triggers the `SubTaskActivity` when an item is selected, and sends the task information using the `GetIdListener` interface.
    * Provides a callback (`onUpdateTaskLongPressListener`) for handling the update of tasks.

**Notes**

* This app offers a good foundation for further development. Consider adding:
    * More robust data storage (using a API Call).
    * Reminders and due dates.
    * Task sorting and filtering.
