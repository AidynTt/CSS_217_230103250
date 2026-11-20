package week12_practice.CoffeeRoasterLab;

public class Main {
    public static void main(String[] args) {

        CoffeeRoasterFacade roaster = new CoffeeRoasterFacade();

        //Batch 1: Light Roast
        System.out.println("=== BATCH 1: Light Roast ===");
        boolean batch1Success = roaster.roastLightProfile(
                "Ethiopia Sidamo", 1200
        );
        System.out.println("Batch 1 Completed Successfully: " + batch1Success);

        //Batch 2: Custom Medium Roast
        System.out.println("\n=== BATCH 2: Medium Roast ===");
        boolean batch2Success = roaster.runRoastBatch(
                "Colombia Supremo", 2500, 48, 212
        );
        System.out.println("Batch 2 Completed Successfully: " + batch2Success);

        //Batch 3: Emergency Test
        System.out.println("\n=== BATCH 3: Emergency Test ===");
        boolean batch3Success = roaster.runRoastBatch(
                "Brazil Santos", 1500, 50, 280
        );
        System.out.println("Batch 3 Completed Successfully: " + batch3Success);

        //Batch 4: Recovery After Abort
        System.out.println("\n=== BATCH 4: Dark Roast ===");
        boolean batch4Success = roaster.roastDarkProfile(
                "Sumatra Mandheling", 1800
        );
        System.out.println("Batch 4 Completed Successfully: " + batch4Success);
    }
}