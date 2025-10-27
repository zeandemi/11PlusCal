// Minimal Java main class created by assistant
// Contract:
// - input: command-line args (String[])
// - output: prints a greeting and any provided args to stdout
// - error modes: none (prints a message if no args)

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class standardisedScore {
    private long daysYoung = 0;

    public void calculateAgeAdjustmentFromDob(String dobString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob = LocalDate.parse(dobString, formatter);
        LocalDate definedDate = LocalDate.of(2014, 9, 1);
        daysYoung = ChronoUnit.DAYS.between(definedDate, dob);
    }

    public int desiredScore;

    public void setRawScores(int score) {
        this.desiredScore = score;
    }

    public double calculateStandardisedScore(int englishScore, int mathScore, boolean conversionType) {
        // Constants for standardisation..
        double englishMean = 34.98670057;
        double englishStdDev = 7.614091721;
        double englishAgeAdjustment = 0.016617024;
        double mathMean = 27.30021514;
        double mathStdDev = 13.07618947;
        double mathAgeAdjustment = 0.00894304;
        if (conversionType) {
            // call method to calculate standardised scores
            double standardisedEnglishScore = calStandardisedScore(englishScore, englishMean, englishStdDev);
            double standardisedMathScore = calStandardisedScore(mathScore, mathMean, mathStdDev);

            // call method to calculate adjusted standardised scores
            double adjustedEnglishScore = calAdjAgeAndStandardisedScore(standardisedEnglishScore, daysYoung,
                    englishAgeAdjustment);
            double adjustedMathScore = calAdjAgeAndStandardisedScore(standardisedMathScore, daysYoung,
                    mathAgeAdjustment);

            double FinalScore = 1.5 * (adjustedEnglishScore + adjustedMathScore);
            return FinalScore;

        } else {

            // call method to calculate standardised scores from adjusted results
            double standardisedEnglishScore = calStandardisedScoreFromResult(desiredScore, daysYoung,
                    englishAgeAdjustment);
            double standardisedMathScore = calStandardisedScoreFromResult(desiredScore, daysYoung,
                    mathAgeAdjustment);
                    
            double englishFinalScore = desiredScore/1.5 - standardisedMathScore;
            double mathFinalScore = desiredScore/1.5 - standardisedEnglishScore;

            // call method to calculate raw scores from standardised scores
            double rawEnglishScore = calRawScoreFromStandardisedScore(englishMean, englishStdDev, standardisedEnglishScore);
            double rawMathScore = calRawScoreFromStandardisedScore(mathMean, mathStdDev, standardisedMathScore);



            double FinalScore = 1.5 * (standardisedEnglishScore + standardisedMathScore);
            return FinalScore;

        }
    }

    private double calStandardisedScore(int rawScore, double mean, double Sdev) {
        // calucculate standardised score using the formula
        double standardisedScore = (((rawScore - mean) / Sdev) * 15) + 100;
        return standardisedScore;
    }

    private double calAdjAgeAndStandardisedScore(double standardised, long agesYoung, double subjectAdjustment) {
        // calculate adjusted standardised score
        double result = standardised + (agesYoung * subjectAdjustment);
        return result;
    }

    private double calRawScoreFromStandardisedScore(double mean, double Sdev, double standardisedScore) {
        // caluculate raw score using the formula
        double derivedScore = standardisedScore - 100;
        double rawScore = ( derivedScore/ 15) * Sdev + mean;
        return rawScore;
    }

    private double calStandardisedScoreFromResult(double desiredScore, long agesYoung, double subjectAdjustment) {
        // calculate adjusted standardised score
        double standardised = desiredScore / (agesYoung * subjectAdjustment);
        return standardised;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        standardisedScore scorer = new standardisedScore();
        System.out.println("Thanks for using our CSSE 11+ Standardised Score Calculator ...");
        System.out.println("Do you want to calculate standardised score of your child's raw score.? (Y/N)");
        if(scanner.next().equalsIgnoreCase("N")){
            System.out.println("This feature is not implemented yet.");
            scanner.close();
            return;
        }else{
            System.out.println("Proceeding to calculate standardised score from raw scores.");
        System.out.print(
                "Please  now enter your child's English score (English Score), Maths score ( Math Score) and date of birth (YYYY-MM-DD): ");
        try {
            int englishScore = scanner.nextInt();
            int mathScore = scanner.nextInt();
            String dobString = scanner.next();
            scorer.calculateAgeAdjustmentFromDob(dobString);
            double finalScore = scorer.calculateStandardisedScore(englishScore, mathScore, true);
            System.out.printf("Your child's standardised score is: %.2f%n", finalScore);
        } catch (Exception e) {
            System.out.println(
                    "Invalid input. Please ensure you enter two integers followed by a date in YYYY-MM-DD format.");
        } finally {
            scanner.close();
        }
        }
    }
}