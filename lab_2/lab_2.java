import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Company{
    private double transactionFee;

    Company(){
        transactionFee = 0;
    }

    void add_transactionFee(double transactionFee){
        this.transactionFee += transactionFee;
    }

    double balance(){
        return transactionFee;
    }
}

class Item{
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String offer;
    private String merchant_name;

    Item(int id, String name, double price, int quantity, String category, String merchant_name){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        offer = "None";
        this.merchant_name = merchant_name;
    }

    void setOffer(String offer){
        this.offer = offer;
    }

    String getOffer(){
        return offer;
    }

    void display(){
        System.out.println(id + " " + name + " " + price + " " + quantity + " " + offer + " " + category);
    }

    double getPrice(){
        return price;
    }

    String getName(){
        return name;
    }

    int getQuantity(){
        return quantity;
    }

    void setPrice(double price){
        this.price = price;
    }

    void setQuantity(int quantity){
        this.quantity = quantity;
    }

    int get_id(){
        return id;
    }

    String getCategory(){
        return category;
    }

    String getMerchant(){
        return merchant_name;
    }
}

interface type{
    public void display_user();
    public void display();
    public void printReward();
    public int getNum();
    public String getName();
}

class Normal_customer implements type{

    private String name;
    private int num;
    private String address;
    private int num_orders;
    private double money;
    private double reward;
    private int count = 0;
    private ArrayList<Item> items = new ArrayList<Item>();
    ArrayList<Item> cart = new ArrayList<Item>();

    Normal_customer(int num, String name, String address){
        this.num = num;
        this.name = name;
        this.address = address;
        num_orders = 0;
        money = 100.0;
    }

    @Override
    public int getNum() {
//        customer id
        return num;
    }

    @Override
    public String getName() {
        return name;
    }

    double getMoney(){
        return money;
    }

    double getReward(){
        return reward;
    }

    void purchase(Item purchased_item){
        items.add(purchased_item);
        System.out.println("Item Successfully bought");
        if(money < purchased_item.getQuantity() * purchased_item.getPrice() * 1.005){
//            transaction fee also has to be paid by customer
//            if money with customer is less than total cost but reward money is sufficient
            reward -= purchased_item.getQuantity() * purchased_item.getPrice() * 1.005- money;
            money = 0;
        }
        else{
            money -= purchased_item.getQuantity() * purchased_item.getPrice() * 1.005;
        }
        count++;
        if(count >= 5){
//            reward earned on more than 5 purchases
            reward += 10;
            count = 0;
        }
        num_orders++;
    }

    void addCart(Item cart_item){
        cart.add(cart_item);
        System.out.println("Item Successfully added to cart");
    }

    void clearCart(){
        cart.clear();
    }

    void order(){
        for(int i = items.size()-1; i >= items.size()-10; i--){
            if(i >= 0){
                int quantity;
                if(items.get(i).getOffer().equals("buy one get one")){
                    quantity = 2*items.get(i).getQuantity();
//                    if buy one get one then quantity would be doubled
                }
                else{
                    quantity = items.get(i).getQuantity();
                }
                System.out.println("Bought item " +  items.get(i).getName() + " " + quantity + " for Rs " + items.get(i).getPrice()*items.get(i).getQuantity() +  " from Merchant " + items.get(i).getMerchant());
            }
        }
    }

    @Override
    public void display_user() {
        System.out.println(name + " " + address + " " + num_orders);
    }

    @Override
    public void display() {
        System.out.println(num + " " + name);
    }

    @Override
    public void printReward(){
        System.out.println("Reward earned: " + reward);
    }
}

class Merchant implements type {
    ArrayList<Item> items = new ArrayList<Item>();
    private int itemCount = 0;
    private String name;
    private int num;
    private String address;
    private double contribution;
    private int max_slot = 10;
    private int bonus = 0;

    Merchant(int num, String name, String address) {
        int itemCount = 0;
        this.num = num;
        this.name = name;
        this.address = address;
        contribution = 0;
    }

    @Override
    public int getNum() {
//        merchant id
        return num;
    }

    @Override
    public String getName() {
        return name;
    }

    void addItem(String item_name, double price, int quantity, String category) {
        if(itemCount < max_slot) {
            int id = ((num-1)*5 + (itemCount + 1));
            Item item = new Item(id, item_name, price, quantity, category, this.name);
            items.add(item);
            item.display();
            itemCount++;
        }
        else{
//            if number of items is more than max slot of merchant
            System.out.println("Limit exceeded");
        }
    }

    void editItem(int id, double price, int quantity) {
        for (Item item : items) {
            if (item.get_id() == id) {
                item.setPrice(price);
                item.setQuantity(quantity);
                item.display();
            }
        }
    }

    void addOffer(int id, String offer) {
        for (Item item : items) {
            if (item.get_id() == id) {
                if(offer.equals("buy one get one") && item.getQuantity() == 1){
//                    offer is nullified as there is only one quantity
                    item.display();
                    break;
                }
                item.setOffer(offer);
                item.display();
            }
        }
    }

    void display_item() {
        for (Item item : items) {
            item.display();
        }
    }

    private void increase_maxSlot(){
//        reward is earned
        max_slot++;
        bonus++;
    }

    void sold(Item i){
        for(Item item: items){
            contribution += 0.005*i.getPrice()*i.getQuantity();
//            contribution made to company
            if(item.getName().equals(i.getName())){
//                matching item sold from all the items with the merchant
                if(item.getOffer().equals("buy one get one")){
                    item.setQuantity(item.getQuantity() - 2*i.getQuantity());
                }
                else{
                    item.setQuantity(item.getQuantity() - i.getQuantity());
                }
            }
        }
        if(contribution >= 100){
            this.increase_maxSlot();
        }
    }

    @Override
    public void display_user() {
        System.out.println(name + " " + address + " " + contribution);
    }

    @Override
    public void display() {
        System.out.println(num + " " + name);
    }

    @Override
    public void printReward() {
        System.out.println("Reward slots: " + bonus);
    }
}

public class lab_2 {
    public static void main(String[] args) {
        Company company = new Company();
        ArrayList<Merchant> merchants = new ArrayList<Merchant>();
//        initialise merchants
        Merchant m1 = new Merchant(1, "jack", "a");
        Merchant m2 = new Merchant(2, "john", "b");
        Merchant m3 = new Merchant(3, "james", "c");
        Merchant m4 = new Merchant(4, "jeff", "d");
        Merchant m5 = new Merchant(5, "joseph", "e");
        merchants.add(m1);
        merchants.add(m2);
        merchants.add(m3);
        merchants.add(m4);
        merchants.add(m5);

        ArrayList<Normal_customer> customers = new ArrayList<Normal_customer>();
//        initialise customers
        Normal_customer c1 = new Normal_customer(1, "ali", "f");
        Normal_customer c2 = new Normal_customer(2, "nobby", "g");
        Normal_customer c3 = new Normal_customer(3, "bruno", "h");
        Normal_customer c4 = new Normal_customer(4, "borat", "i");
        Normal_customer c5 = new Normal_customer(5, "aladeen", "j");
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
        customers.add(c4);
        customers.add(c5);

        Scanner scan = new Scanner(System.in);
        while (true){
//            initial menu
            System.out.println("Welcome to Mercury");
            System.out.println("1) Enter as Merchant");
            System.out.println("2) Enter as Customer");
            System.out.println("3) See user details");
            System.out.println("4) Company account balance");
            System.out.println("5) Exit");
            int query = scan.nextInt();
            if (query == 1) {
                //            Merchant
                for (Merchant choice : merchants) {
                    choice.display();
                }
                int merchant_choice = scan.nextInt();
                for (Merchant merchant : merchants) {
                    if (merchant.getNum() == merchant_choice) {
                        while (true) {
//                            merchant menu
                            System.out.println("Welcome " + merchant.getName());
                            System.out.println("Merchant Menu");
                            System.out.println("1) Add item");
                            System.out.println("2) Edit item");
                            System.out.println("3) Search by category");
                            System.out.println("4) Add offer");
                            System.out.println("5) Rewards won");
                            System.out.println("6) Exit");
                            int merchant_query = scan.nextInt();
                            if (merchant_query == 1) {
                                // add item
                                System.out.println("Enter item details");
                                scan.nextLine();
                                System.out.println("item name:");
                                String item_name = scan.nextLine();
                                System.out.println("item price:");
                                double price = scan.nextDouble();
                                System.out.println("item quantity:");
                                int quantity = scan.nextInt();
                                scan.nextLine();
                                System.out.println("item category:");
                                String category = scan.nextLine();
                                merchant.addItem(item_name, price, quantity, category);
                            }
                            if (merchant_query == 2) {
                                // edit item
                                System.out.println("choose item by code");
                                merchant.display_item();
                                int id = scan.nextInt();
                                System.out.println("Enter edit details");
                                System.out.println("item price:");
                                double edit_price = scan.nextDouble();
                                System.out.println("item quantity:");
                                int edit_quantity = scan.nextInt();
                                merchant.editItem(id, edit_price, edit_quantity);
                            }
                            if (merchant_query == 3) {
//                                search by category
                                HashMap<String, ArrayList<Item>> map = new HashMap<>();
//                                hashmap of category with list of items in that category
                                for(Merchant merchant_iter: merchants){
                                    for(Item i:merchant_iter.items){
                                        if(map.containsKey(i.getCategory())){
//                                            if category already in hashmap add additional item
                                            ArrayList<Item> items = map.get(i.getCategory());
                                            items.add(i);
                                            map.put(i.getCategory(), items);
                                        }
                                        else{
//                                            if category not in hashmap add category along with item
                                            ArrayList<Item> items = new ArrayList<Item>();
                                            items.add(i);
                                            map.put(i.getCategory(), items);
                                        }
                                    }
                                }
                                System.out.println("Choose a category");
                                int count = 1;
                                HashMap<Integer, String> categories = new HashMap<>();
//                                hashmap of category choice number with category name
                                for (HashMap.Entry<String,ArrayList<Item>> entry : map.entrySet()){
                                    System.out.println(count + ") " + entry.getKey());
                                    categories.put(count, entry.getKey());
                                    count++;
                                }
                                int n = scan.nextInt();
                                String chosen_category = categories.get(n);
                                ArrayList<Item> category_items = map.get(chosen_category);
                                for(Item i:category_items){
//                                    display elements in that category
                                    i.display();
                                }

                            }
                            if (merchant_query == 4) {
                                //  add offer
                                System.out.println("choose item by code");
                                merchant.display_item();
                                int id = scan.nextInt();
                                System.out.println("choose offer");
                                System.out.println("1) buy one get one");
                                System.out.println("2) 25% off");
                                int offer = scan.nextInt();
                                if (offer == 1) {
                                    merchant.addOffer(id, "buy one get one");
                                }
                                if(offer == 2){
                                    merchant.addOffer(id, "25% off");
                                }
                            }
                            if (merchant_query == 5) {
                                //rewards won
                                merchant.printReward();
                            }
                            if (merchant_query == 6) {
//                                exit
                                break;
                            }
                        }
                    }
                }

            }

            if (query == 2) {
                // Customer
                for (Normal_customer choice : customers) {
                    choice.display();
                }
                int customer_choice = scan.nextInt();
                for (Normal_customer customer : customers) {
                    if (customer.getNum() == customer_choice) {
                        while (true) {
//                            customer menu
                            System.out.println("Welcome " + customer.getName());
                            System.out.println("Customer Menu");
                            System.out.println("1) Search item");
                            System.out.println("2) checkout cart");
                            System.out.println("3) Reward won");
                            System.out.println("4) print latest orders");
                            System.out.println("5) Exit");
                            int customer_query = scan.nextInt();

                            if(customer_query == 1){
//                                search item
                                HashMap<String, ArrayList<Item>> map = new HashMap<>();
//                                hashmap of category with list of items in that category
                                for(Merchant merchant_iter: merchants){
                                    for(Item i:merchant_iter.items){
                                        if(map.containsKey(i.getCategory())){
//                                            if category already in hashmap add additional item
                                            ArrayList<Item> items = map.get(i.getCategory());
                                            items.add(i);
                                            map.put(i.getCategory(), items);
                                        }
                                        else{
//                                            if category not in hashmap add category along with item
                                            ArrayList<Item> items = new ArrayList<Item>();
                                            items.add(i);
                                            map.put(i.getCategory(), items);
                                        }
                                    }
                                }
                                System.out.println("Choose a category");
                                int count = 1;
                                HashMap<Integer, String> categories = new HashMap<>();
//                                hashmap of category choice with category name
                                for (HashMap.Entry<String,ArrayList<Item>> entry : map.entrySet()){
                                    System.out.println(count + ") " + entry.getKey());
                                    categories.put(count, entry.getKey());
                                    count++;
                                }
                                int n = scan.nextInt();
                                String chosen_category = categories.get(n);
                                ArrayList<Item> category_items = map.get(chosen_category);
                                System.out.println("choose item by code");
                                for(Item i:category_items){
//                                    display all items in that category
                                    i.display();
                                }
                                System.out.println("Enter item code");
                                int code = scan.nextInt();
                                System.out.println("Enter item quantity");
                                int quantity = scan.nextInt();
                                System.out.println("Choose method of transaction");
                                System.out.println("1) Buy item");
                                System.out.println("2) Add item to cart");
                                System.out.println("3) Exit");
                                int option = scan.nextInt();
                                if(option != 3){
//                                    not exit
                                    for(Item i:category_items) {
                                        if (i.get_id() == code) {
                                            if (option == 1) {
                                                //                                            directly buy
                                                double cost = i.getPrice();
                                                if (i.getOffer().equals("25% off")) {
                                                    cost *= 0.75;
                                                }
                                                if (i.getOffer().equals("buy one get one")) {
                                                    if (quantity > 1) {
                                                        if (quantity % 2 == 0) {
                                                            // if number of items is even reduce it by half
                                                            //if there is buy one get one free offer and customer wants to buy 2 he need only buy 1
                                                            quantity /= 2;
                                                        } else {
                                                            // if number of items is odd
                                                            quantity = quantity / 2 + 1;
                                                        }
                                                    }
                                                }
                                                if (i.getQuantity() >= quantity && (quantity * cost * 1.005) <= customer.getMoney() + customer.getReward()) {
                                                    //    customer has to pay additional transaction fee of 0.5%
                                                    //   if it is possible to buy
                                                    Item purchased = new Item(i.get_id(), i.getName(), cost, quantity, i.getCategory(), i.getMerchant());
                                                    purchased.setOffer(i.getOffer());
                                                    customer.purchase(purchased);
                                                    company.add_transactionFee(0.01 * purchased.getQuantity() * purchased.getPrice());
                                                    for (Merchant merchant : merchants) {
                                                        if (merchant.getName().equals(i.getMerchant())) {
                                                            //   getting which merchant sold
                                                            merchant.sold(purchased);
                                                        }
                                                    }
                                                } else {
                                                    if (i.getQuantity() < quantity) {
                                                        System.out.println("Out of stock");
                                                    } else if ((quantity * cost * 1.005) > customer.getMoney() + customer.getReward()) {
                                                        System.out.println("Out of money");
                                                    }
                                                }
                                            }
                                            if (option == 2) {
                                                //  adding to cart
                                                Item purchased = new Item(i.get_id(), i.getName(), i.getPrice(), quantity, i.getCategory(), i.getMerchant());
                                                purchased.setOffer(i.getOffer());
                                                customer.addCart(purchased);
                                            }
                                        }
                                    }
                                }

                            }
                            if(customer_query == 2){
//                                checkout cart
                                System.out.println("Checkout cart");
                                ArrayList<Item> allItems = new ArrayList<Item>();
//                                getting list of all possible items with all merchants
                                for(Merchant merchant: merchants){
                                    allItems.addAll(merchant.items);
                                }
                                for(Item cart_item: customer.cart){
                                    for(Item i: allItems){
//                                        matching cart item in the all_items list
                                        if(cart_item.get_id() == i.get_id()){
                                            double cost = i.getPrice();
                                            if(i.getOffer().equals("25% off")){
                                                cost *= 0.75;
                                            }
                                            if(i.getOffer().equals("buy one get one")){
                                                int quantity = cart_item.getQuantity();
                                                if(quantity > 1){
                                                    if(quantity % 2 == 0){
//                                                        even quantity
                                                        quantity /= 2;
                                                    }
                                                    else{
//                                                        odd quantity
                                                        quantity = quantity/2 + 1;
                                                    }
                                                }
                                                cart_item.setQuantity(quantity);
                                            }
                                            if(i.getQuantity() >= cart_item.getQuantity() && (cart_item.getQuantity()*cost*1.005) <= customer.getMoney() + customer.getReward()){
//                                                if it is possible to buy
                                                Item purchased = new Item(i.get_id(), i.getName(), cost, cart_item.getQuantity(), i.getCategory(), i.getMerchant());
                                                customer.purchase(purchased);
                                                company.add_transactionFee(0.01 * purchased.getPrice() * purchased.getQuantity());
//                                                total cost is price * quantity
                                                for(Merchant merchant: merchants){
                                                    if(merchant.getName().equals(i.getMerchant())){
//                                                        getting which merchant sold
                                                        merchant.sold(purchased);
                                                    }
                                                }
                                            }
                                            else{
                                                if(i.getQuantity() < cart_item.getQuantity()){
                                                    System.out.println("Out of stock");
                                                }
                                                else if((cart_item.getQuantity()*cost*1.005) > customer.getMoney() + customer.getReward()){
                                                    System.out.println("Out of money");
                                                }
                                            }
                                        }
                                    }
                                }
                                customer.clearCart();
                            }
                            if(customer_query == 3){
//                                rewards won
                                customer.printReward();
                            }
                            if(customer_query == 4){
//                                print latest orders
                                customer.order();
                            }
                            if(customer_query == 5){
//                                exit
                                break;
                            }
                        }
                    }
                }
            }

            if (query == 3) {
                // user details
                System.out.println("Merchants");
                for (Merchant choice : merchants) {
                    choice.display();
                }
                System.out.println("Customers");
                for (Normal_customer choice : customers) {
                    choice.display();
                }
                scan.nextLine();
                char type = scan.next().charAt(0);
                int id = scan.nextInt();
                if(type == 'M'){
//                    if merchant
                    for (Merchant merchant : merchants) {
                        if (merchant.getNum() == id) {
                            merchant.display_user();
                        }
                    }
                }
                if(type == 'C'){
//                    if customer
                    for (Normal_customer customer : customers) {
                        if (customer.getNum() == id) {
                            customer.display_user();
                        }
                    }
                }
            }

            if (query == 4) {
                // company account balance
                double company_balance = company.balance();
                System.out.println("Company Account Balance = " + company_balance);
            }

            if (query == 5) {
                // exit
                System.exit(0);
            }
        }
    }
}
