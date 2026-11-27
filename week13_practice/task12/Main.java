package week13_practice.task12;

import java.util.*;

class Cart {
    double total;
    int itemCount;
    String coupon;

    public Cart(double total, int itemCount, String coupon) {
        this.total = total;
        this.itemCount = itemCount;
        this.coupon = coupon;
    }
}

class Member {
    String name;
    String tier;

    public Member(String name, String tier) {
        this.name = name;
        this.tier = tier;
    }
}

interface DiscountStrategy {
    double calculateDiscount(Cart cart, Member member);
}

class SeasonalPercentageDiscount implements DiscountStrategy {
    public double calculateDiscount(Cart cart, Member member) {
        return cart.total * 0.15;
    }
}

class TieredLoyaltyDiscount implements DiscountStrategy {
    public double calculateDiscount(Cart cart, Member member) {
        if (member.tier.equals("Gold")) {
            return cart.total * 0.20;
        } else if (member.tier.equals("Silver")) {
            return cart.total * 0.10;
        }

        return cart.total * 0.05;
    }
}

class BundleThresholdDiscount implements DiscountStrategy {
    public double calculateDiscount(Cart cart, Member member) {
        if (cart.itemCount >= 5) {
            return cart.total * 0.25;
        }

        return 0;
    }
}

class CartAnalyzer {
    public boolean analyze(Cart cart) {
        System.out.println("Analyzing cart...");
        System.out.println("Items: " + cart.itemCount);

        return cart.total > 0 && cart.itemCount > 0;
    }
}

class MemberProfileService {
    private final Map<String, Member> members = new HashMap<>();

    public MemberProfileService() {
        members.put("user01", new Member("Alex", "Gold"));
        members.put("user02", new Member("John", "Silver"));
    }

    public Member getMember(String userId) {
        return members.get(userId);
    }
}

class CouponValidator {
    public boolean validate(String coupon) {
        return "SALE2026".equals(coupon);
    }
}

class PointsLedger {
    private final Map<String, Integer> balances =
            new HashMap<>();

    public void addPoints(String userId, int points) {
        int current = balances.getOrDefault(userId, 0);

        balances.put(userId, current + points);

        System.out.println("Points earned: " + points);
        System.out.println("Total reward points: "
                + balances.get(userId));
    }
}

class BillingFacade {
    private DiscountStrategy strategy;

    private final CartAnalyzer analyzer =
            new CartAnalyzer();

    private final MemberProfileService profiles =
            new MemberProfileService();

    private final CouponValidator validator =
            new CouponValidator();

    private final PointsLedger ledger =
            new PointsLedger();

    public BillingFacade(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public void finalizeCart(String userId, Cart cart) {

        if (!analyzer.analyze(cart)) {
            System.out.println("Invalid cart");
            return;
        }

        Member member = profiles.getMember(userId);

        if (member == null) {
            System.out.println("Member not found");
            return;
        }

        System.out.println("Customer: " + member.name);
        System.out.println("Membership: " + member.tier);

        if (!validator.validate(cart.coupon)) {
            System.out.println("Invalid coupon");
            return;
        }

        System.out.println("Coupon validated");

        double discount =
                strategy.calculateDiscount(cart, member);

        double finalPrice = cart.total - discount;

        System.out.printf("Original price: $%.2f%n",
                cart.total);

        System.out.printf("Discount: $%.2f%n",
                discount);

        System.out.printf("Final price: $%.2f%n",
                finalPrice);

        int points = (int) (finalPrice / 10);

        ledger.addPoints(userId, points);

        System.out.println("Checkout completed!");
    }
}

public class Main {
    public static void main(String[] args) {

        Cart cart = new Cart(200, 5, "SALE2026");

        BillingFacade facade =
                new BillingFacade(
                        new SeasonalPercentageDiscount());

        System.out.println("Seasonal Discount:");
        facade.finalizeCart("user01", cart);

        System.out.println("\nLoyalty Discount:");
        facade.setStrategy(new TieredLoyaltyDiscount());
        facade.finalizeCart("user01", cart);

        System.out.println("\nBundle Discount:");
        facade.setStrategy(new BundleThresholdDiscount());
        facade.finalizeCart("user01", cart);

        System.out.println("\nInvalid Coupon Test:");
        Cart invalidCart = new Cart(200, 5, "WRONG");
        facade.finalizeCart("user01", invalidCart);
    }
}
