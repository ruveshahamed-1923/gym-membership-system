import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

class Member {
    int id, age;
    String name, phone, plan;
    LocalDate joinDate, expiryDate;
    boolean active = true;

    Member(int id, String name, int age, String phone,
           String plan, int months) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.plan = plan;
        joinDate = LocalDate.now();
        expiryDate = joinDate.plusMonths(months);
    }

    void display() {
        System.out.println("\nID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Phone: " + phone);
        System.out.println("Plan: " + plan);
        System.out.println("Joining Date: " + joinDate);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("Fee: Rs." + getFee());
        System.out.println("Status: " +
            (active && !expiryDate.isBefore(LocalDate.now())
             ? "Active" : "Expired/Cancelled"));
    }

    int getFee() {
        if (plan.equalsIgnoreCase("Monthly"))
            return 1000;
        else if (plan.equalsIgnoreCase("Quarterly"))
            return 2500;
        else
            return 8000;
    }
}

public class Main {
    static ArrayList<Member> members = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static Member findById(int id) {
        for (Member m : members)
            if (m.id == id)
                return m;
        return null;
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n--- GYM MEMBERSHIP SYSTEM ---");
            System.out.println("1. Register Member");
            System.out.println("2. Display All Members");
            System.out.println("3. Search by ID");
            System.out.println("4. Search by Name");
            System.out.println("5. Display Active Members");
            System.out.println("6. Display Expired Members");
            System.out.println("7. Renew Membership");
            System.out.println("8. Cancel Membership");
            System.out.println("9. Display Expiry Date");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    if (findById(id) != null) {
                        System.out.println("ID already exists!");
                        break;
                    }

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    System.out.println(
                        "Plans: Monthly, Quarterly, Yearly");
                    System.out.print("Enter Plan: ");
                    String plan = sc.nextLine();

                    int months;
                    if (plan.equalsIgnoreCase("Monthly"))
                        months = 1;
                    else if (plan.equalsIgnoreCase("Quarterly"))
                        months = 3;
                    else if (plan.equalsIgnoreCase("Yearly"))
                        months = 12;
                    else {
                        System.out.println("Invalid plan!");
                        break;
                    }

                    Member m = new Member(
                        id, name, age, phone, plan, months);
                    members.add(m);
                    System.out.println("Registration successful!");
                    System.out.println("Membership Fee: Rs."
                                       + m.getFee());
                    break;

                case 2:
                    if (members.isEmpty())
                        System.out.println("No members found.");
                    for (Member x : members)
                        x.display();
                    break;

                case 3:
                    System.out.print("Enter Member ID: ");
                    Member a = findById(sc.nextInt());
                    if (a != null)
                        a.display();
                    else
                        System.out.println("Member not found.");
                    break;

                case 4:
                    System.out.print("Enter Name: ");
                    String search = sc.nextLine();
                    boolean found = false;
                    for (Member x : members) {
                        if (x.name.equalsIgnoreCase(search)) {
                            x.display();
                            found = true;
                        }
                    }
                    if (!found)
                        System.out.println("Member not found.");
                    break;

                case 5:
                    for (Member x : members)
                        if (x.active &&
                            !x.expiryDate.isBefore(LocalDate.now()))
                            x.display();
                    break;

                case 6:
                    for (Member x : members)
                        if (x.expiryDate.isBefore(LocalDate.now()))
                            x.display();
                    break;

                case 7:
                    System.out.print("Enter Member ID: ");
                    Member r = findById(sc.nextInt());
                    if (r != null) {
                        System.out.print("Renew for months (1/3/12): ");
                        int monthsRenew = sc.nextInt();
                        if (monthsRenew == 1 || monthsRenew == 3
                                || monthsRenew == 12) {
                            LocalDate today = LocalDate.now();
                            if (!r.expiryDate.isBefore(today))
                                r.expiryDate =
                                    r.expiryDate.plusMonths(monthsRenew);
                            else
                                r.expiryDate =
                                    today.plusMonths(monthsRenew);
                            r.active = true;
                            System.out.println("Membership renewed!");
                        } else {
                            System.out.println("Invalid duration!");
                        }
                    } else
                        System.out.println("Member not found.");
                    break;

                case 8:
                    System.out.print("Enter Member ID: ");
                    Member c = findById(sc.nextInt());
                    if (c != null) {
                        c.active = false;
                        System.out.println("Membership cancelled.");
                    } else
                        System.out.println("Member not found.");
                    break;

                case 9:
                    System.out.print("Enter Member ID: ");
                    Member e = findById(sc.nextInt());
                    if (e != null)
                        System.out.println("Expiry Date: "
                                           + e.expiryDate);
                    else
                        System.out.println("Member not found.");
                    break;

                case 10:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 10);

        sc.close();
    }
}