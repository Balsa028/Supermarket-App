package com.balsa.onlinesupermarket.DatabaseFiles;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.balsa.onlinesupermarket.Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Database(entities = {Item.class,CartItem.class},version = 1)
public abstract class ShopDatabase extends RoomDatabase {
    public abstract ItemDao getItemDao();
    public abstract CartItemDao getCartItemDao();


    private static ShopDatabase instance;

    public static synchronized ShopDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context,ShopDatabase.class,"shop_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //populate initial data threw async task
            new PopulateInitialData(instance).execute();
        }
    };

    private static class PopulateInitialData extends AsyncTask<Void,Void,Void>{

        private ItemDao itemDao;

        public PopulateInitialData(ShopDatabase db) {
            itemDao = db.getItemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<Item> allItems = new ArrayList<>();

            Item tomato = new Item("Tomato", "The tomato is the edible berry of the plant Solanum lycopersicum,commonly known as a tomato plant. " +
                    "The species originated in western South America and Central America.The Nahuatl (the language used by the Aztecs) word tomatl gave rise to the Spanish word tomate," +
                    " from which the English word tomato derived. Its domestication and use as a cultivated food may have originated with the indigenous peoples of Mexico."
                    , "https://agfstorage.blob.core.windows.net/misc/HD_com/2020/02/04/savorino.jpg", "Vegatables", 150, 20);
            allItems.add(tomato);

            Item milk = new Item("Milk", "Milk (also known in unfermented form as sweet milk) is a nutrient-rich liquid food produced by the mammary glands of mammals." +
                    " It is the primary source of nutrition for young mammals, including breastfed human infants before they are able to digest solid food. Early-lactation milk is called colostrum," +
                    " which contains antibodies that strengthen the immune system and thus reduces the risk of many diseases.", "https://www.stonyfield.com/wp-content/uploads/2017/02/stonyfield-organic-milk-half-gallon-whole-500x500-1.png",
                    "Dairy", 119, 15);
            allItems.add(milk);

            Item carrot = new Item("Carrot", "The carrot (Daucus carota subsp. sativus) is a root vegetable, usually orange in color, though purple, black, red, white, and yellow cultivars exist.They are a domesticated form of the wild carrot," +
                    " Daucus carota, native to Europe and Southwestern Asia. The plant probably originated in Persia and was originally cultivated for its leaves and seeds. The most commonly eaten part of the plant is the taproot, although the stems and leaves are also eaten." +
                    " The domestic carrot has been selectively bred for its greatly enlarged, more palatable, less woody-textured taproot.", "https://befreshcorp.net/wp-content/uploads/2017/06/product-packshot-Carrot-558x600.jpg", "Vegatables", 250, 20);
            allItems.add(carrot);

            Item fanta = new Item("Fanta", "Fanta is a brand of fruit-flavored carbonated soft drinks created by Coca-Cola Deutschland under the leadership of German businessman Max Keith. There are more than 150 flavors worldwide. Fanta originated as a Coca-Cola substitute in 1940 due to the " +
                    "American trade embargo of Nazi Germany which affected the availability of Coca-Cola ingredients; the current formulation was developed in Italy.", "https://diskontpicapazova.com/wp-content/uploads/2014/03/snapshotimagehandler_1162369759.jpeg", "Drink", 120, 20);
            allItems.add(fanta);

            Item yogurth = new Item("Yogurt", "Yogurt (UK: /ˈjɒɡərt/; US: /ˈjoʊɡərt/,from Turkish: yoğurt) also spelled yoghurt, yogourt or yoghourt, is a food produced by bacterial fermentation of milk.[2] The bacteria used to make yogurt are known as yogurt cultures. Fermentation of sugars in the milk by these bacteria produces" +
                    " lactic acid, which acts on milk protein to give yogurt its texture and characteristic tart flavor. Cow's milk is the milk most commonly used to make yogurt. Milk from water buffalo, goats, ewes, mares, camels, yaks and plant milks are also used to produce yogurt. The milk used may be homogenized or not." +
                    " It may be pasteurized or raw. Each type of milk produces substantially different results.", "https://images-na.ssl-images-amazon.com/images/I/61GcEwvDa3L._SX466_PIbundle-6,TopRight,0,0_SX466SY350SH20_.jpg", "Dairy", 150, 50);
            allItems.add(yogurth);

            Item detergent = new Item("Detergent", "A detergent is a surfactant or a mixture of surfactants with cleansing properties in dilute solutions. These substances are usually alkylbenzene sulfonates, a family of compounds that are similar to soap but are more soluble in hard water, because the polar sulfonate (of detergents) is less likely than the polar" +
                    " carboxylate (of soap) to bind to calcium and other ions found in hard water.", "https://images-na.ssl-images-amazon.com/images/I/81AOJOz8sJL._AC_SL1500_.jpg", "Cleanser", 700, 10);
            allItems.add(detergent);

            Item cocaCola = new Item("Coca Cola", "Coca-Cola, or Coke, is a carbonated soft drink manufactured by The Coca-Cola Company. Originally marketed as a temperance drink and intended as a patent medicine," +
                    " it was invented in the late 19th century by John Stith Pemberton and was bought out by businessman Asa Griggs Candler, whose marketing tactics led Coca-Cola to its dominance of the world soft-drink market throughout the 20th century." +
                    " The drink's name refers to two of its original ingredients: coca leaves, and kola nuts (a source of caffeine). The current formula of Coca-Cola remains a trade secret; however, a variety of reported recipes and experimental recreations have been published.",
                    "https://paralelaplus.rs/wp-content/uploads/2020/12/Coca-Cola-1.5l.jpg", "Drink", 120, 120);
            allItems.add(cocaCola);

            for(Item item:allItems){
                itemDao.insertItem(item);
            }

            return null;
        }
    }
}
