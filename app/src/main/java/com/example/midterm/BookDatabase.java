package com.example.midterm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.io.ByteArrayOutputStream;
import java.time.Year;
import java.util.Date;

/**
 * Database class / Entity
 * Provides access to the database, as well as creates new database entries upon creation.
 * Can edit/update/add database entries by modifying the addStarterData method, the version
 * and the DATABASE_NAME field.
 */

@Database(entities = {Book.class}, version = 5, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "book5.db";
    private static BookDatabase mBookDatabase;

    // Singleton
    public static BookDatabase getInstance(Context context) {
        if (mBookDatabase == null) {
            mBookDatabase = Room.databaseBuilder(context, BookDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            mBookDatabase.addStarterData();
        }

        return mBookDatabase;
    }

    // Access to DAO to modify database
    public abstract BookDAO bookDAO();

    // Initializes database entries
    private void addStarterData(){
        if (bookDAO().getBookList().size() == 0){
            runInTransaction(() -> {
                Book newBook = new Book("The Hobbit", Genre.Fantasy, "J. R. R. Tolkien", 1936,
                        "The Hobbit is set within Tolkien's fictional universe and follows the " +
                                "quest of home-loving Bilbo Baggins, the titular hobbit, to win a share of the " +
                                "treasure guarded by a dragon named Smaug. Bilbo's journey takes him from his " +
                                "light-hearted, rural surroundings into more sinister territory.", 294, "2009", 4);
                bookDAO().insertBook(newBook);

                newBook = new Book("Harry Potter: The Philosophers Stone", Genre.Fantasy, "J. K. Rowling", 1997,
                        "It follows Harry Potter, a young wizard who discovers his magical heritage on his " +
                                "eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft " +
                                "and Wizardry. Harry makes close friends and a few enemies during his first year at the school, " +
                                "and with the help of his friends, he faces an attempted comeback by the dark wizard Lord Voldemort, " +
                                "who killed Harry's parents, but failed to kill Harry when he was just 15 months old.", 294, "2009", 4);
                bookDAO().insertBook(newBook);

                newBook = new Book("The Little Prince", Genre.Novella, "Antoine de Saint-Exupery", 1943,
                        "A pilot stranded in the desert awakes one morning to see, standing before him, " +
                        "the most extraordinary little fellow. \"Please,\" asks the stranger, \"draw me a sheep.\" And the pilot realizes " +
                        "that when life's events are too difficult to understand, there is no choice but to succumb to their mysteries. He pulls " +
                        "out pencil and paper... And thus begins this wise and enchanting fable that, in teaching the secret of what is really " +
                        "important in life, has changed forever the world for its readers.", 294, "2009", 4);
                bookDAO().insertBook(newBook);

                newBook = new Book("Dream of the Red Chamber", Genre.Family_Saga, "Cao Xueqin", 1899,
                        "The Dream of the Red Chamber is considered the greatest novel of Chinese literature. The " +
                        "son of a wealthy, noble family is born with a magic stone in his mouth. A darling and admirer of all the women and girls " +
                        "in the household, he rebels against his stern father and the social barriers of the time.", 294, "2009", 4);
                bookDAO().insertBook(newBook);

                newBook = new Book("And Then There Were None", Genre.Mystery, "Agatha Christie", 1939,
                        "1939. Europe teeters on the brink of war. Ten strangers are invited to Soldier Island, an " +
                        "isolated rock near the Devon coast. Cut off from the mainland, with their generous hosts Mr and Mrs U.N. Owen mysteriously " +
                        "absent, they are each accused of a terrible crime. When one of the party dies suddenly they realise they may be harbouring a " +
                        "murderer among their number.", 294, "2009", 4);
                bookDAO().insertBook(newBook);

                newBook = new Book("The Lion, the Witch, and the Wardrobe", Genre.Fantasy, "C. S. Lewis",1950,
                        "The novel is about four siblings – Peter, Susan, Edmund, and Lucy Pevensie – who are evacuated " +
                        "from London during the Second World War and sent to live with a professor in the English countryside. One day, Lucy discovers " +
                        "that one of the wardrobes in the house contains a portal through to another world, a land covered in snow.", 294, "2009", 4);
                bookDAO().insertBook(newBook);

                newBook = new Book("She: A History of Adventure", Genre.Adventure, "H. Rider Haggard", 1897,
                        "The story is a first-person narrative which follows the journey of Horace Holly and his ward " +
                        "Leo Vincey to a lost kingdom in the African interior. They encounter a primitive race of natives and a mysterious white queen " +
                        "named Ayesha who reigns as the all-powerful \"She\" or \"She-who-must-be-obeyed\".", 294, "2009", 4);
                bookDAO().insertBook(newBook);

                newBook = new Book("The Adventures of Pinocchio", Genre.Fantasy, "Carlo Collodi", 1881,
                        "It chronicles the adventures of a wooden puppet whose lonely maker, Geppetto, wishes were a real " +
                        "boy. A fairy grants his wish by bringing the puppet, Pinocchio, to life, but she tells Pinocchio that he must prove his worth " +
                        "before she will make him into a human boy.", 294, "2009", 4);
                bookDAO().insertBook(newBook);

                newBook = new Book("The Da Vinci Code", Genre.Mystery, "Dan Brown", 2003,
                        "The Da Vinci Code follows \"symbologist\" Robert Langdon and cryptologist Sophie Neveu after a murder " +
                        "in the Louvre Museum in Paris causes them to become involved in a battle between the Priory of Sion and Opus Dei over the possibility " +
                        "of Jesus Christ and Mary Magdalene having had a child together.", 294, "2009", 4);
                bookDAO().insertBook(newBook);

                newBook = new Book("The Alchemist", Genre.Fantasy, "Paulo Coelho", 1988,
                        "The Alchemist is a classic novel in which a boy named Santiago embarks on a journey seeking treasure in the " +
                        "Egyptian pyramids after having a recurring dream about it and on the way meets mentors, falls in love, and most importantly, learns the true " +
                        "importance of who he is and how to improve himself and focus on what really matters in life.", 294, "2009", 4);
                bookDAO().insertBook(newBook);
            });
        }
    }
}
