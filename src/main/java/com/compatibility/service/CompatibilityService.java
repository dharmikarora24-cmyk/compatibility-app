package com.compatibility.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CompatibilityService {

    private static final Map<String, Double> SCORES = new HashMap<>();

    static {
        // Q1 - The Thief (G=2, Y=1, R=3)
        SCORES.put("0_1_1",71.0); SCORES.put("0_2_2",88.0); SCORES.put("0_3_3",32.0);
        SCORES.put("0_1_2",78.0); SCORES.put("0_2_1",74.0); SCORES.put("0_1_3",41.0);
        SCORES.put("0_3_1",38.0); SCORES.put("0_2_3",52.0); SCORES.put("0_3_2",29.0);

        // Q2 - The Wallet (G=1, Y=3, R=2) wait no (R=1, G=2, Y=3)
        SCORES.put("1_1_1",38.0); SCORES.put("1_2_2",91.0); SCORES.put("1_3_3",68.0);
        SCORES.put("1_1_2",31.0); SCORES.put("1_2_1",35.0); SCORES.put("1_1_3",52.0);
        SCORES.put("1_3_1",55.0); SCORES.put("1_2_3",74.0); SCORES.put("1_3_2",61.0);

        // Q3 - The Exam (R=1, G=2, Y=3)
        SCORES.put("2_1_1",41.0); SCORES.put("2_2_2",89.0); SCORES.put("2_3_3",67.0);
        SCORES.put("2_1_2",28.0); SCORES.put("2_2_1",32.0); SCORES.put("2_1_3",51.0);
        SCORES.put("2_3_1",54.0); SCORES.put("2_2_3",76.0); SCORES.put("2_3_2",58.0);

        // Q4 - The Scratch (R=1, G=2, Y=3)
        SCORES.put("3_1_1",34.0); SCORES.put("3_2_2",92.0); SCORES.put("3_3_3",64.0);
        SCORES.put("3_1_2",22.0); SCORES.put("3_2_1",27.0); SCORES.put("3_1_3",44.0);
        SCORES.put("3_3_1",48.0); SCORES.put("3_2_3",73.0); SCORES.put("3_3_2",55.0);

        // Q5 - The Extra Change (R=1, G=2, Y=3)
        SCORES.put("4_1_1",37.0); SCORES.put("4_2_2",88.0); SCORES.put("4_3_3",69.0);
        SCORES.put("4_1_2",24.0); SCORES.put("4_2_1",29.0); SCORES.put("4_1_3",51.0);
        SCORES.put("4_3_1",54.0); SCORES.put("4_2_3",75.0); SCORES.put("4_3_2",62.0);

        // Q6 - The Bus Seat (G=1, R=2, Y=3)
        SCORES.put("5_1_1",84.0); SCORES.put("5_2_2",29.0); SCORES.put("5_3_3",72.0);
        SCORES.put("5_1_2",38.0); SCORES.put("5_2_1",42.0); SCORES.put("5_1_3",76.0);
        SCORES.put("5_3_1",81.0); SCORES.put("5_2_3",51.0); SCORES.put("5_3_2",44.0);

        // Q7 - The Queue (Y=1, G=2, R=3) wait (G=1 accept, G=2 speak, Y=3 step in)
        SCORES.put("6_1_1",74.0); SCORES.put("6_2_2",86.0); SCORES.put("6_3_3",58.0);
        SCORES.put("6_1_2",68.0); SCORES.put("6_2_1",71.0); SCORES.put("6_1_3",52.0);
        SCORES.put("6_3_1",55.0); SCORES.put("6_2_3",63.0); SCORES.put("6_3_2",47.0);

        // Q8 - The Joke (Y=1, G=2, R=3)
        SCORES.put("7_1_1",67.0); SCORES.put("7_2_2",89.0); SCORES.put("7_3_3",31.0);
        SCORES.put("7_1_2",72.0); SCORES.put("7_2_1",69.0); SCORES.put("7_1_3",38.0);
        SCORES.put("7_3_1",34.0); SCORES.put("7_2_3",48.0); SCORES.put("7_3_2",27.0);

        // Q9 - The Midnight Music (Y=1, G=2, G=3)
        SCORES.put("8_1_1",73.0); SCORES.put("8_2_2",88.0); SCORES.put("8_3_3",82.0);
        SCORES.put("8_1_2",61.0); SCORES.put("8_2_1",64.0); SCORES.put("8_1_3",67.0);
        SCORES.put("8_3_1",71.0); SCORES.put("8_2_3",85.0); SCORES.put("8_3_2",79.0);

        // Q10 - The Party Rumour (G=1, G=2, Y=3)
        SCORES.put("9_1_1",86.0); SCORES.put("9_2_2",83.0); SCORES.put("9_3_3",61.0);
        SCORES.put("9_1_2",84.0); SCORES.put("9_2_1",85.0); SCORES.put("9_1_3",68.0);
        SCORES.put("9_3_1",64.0); SCORES.put("9_2_3",71.0); SCORES.put("9_3_2",58.0);

        // Q11 - The Suspicious Friend (G=1, G=2, R=3)
        SCORES.put("10_1_1",82.0); SCORES.put("10_2_2",86.0); SCORES.put("10_3_3",24.0);
        SCORES.put("10_1_2",84.0); SCORES.put("10_2_1",83.0); SCORES.put("10_1_3",31.0);
        SCORES.put("10_3_1",28.0); SCORES.put("10_2_3",34.0); SCORES.put("10_3_2",27.0);

        // Q12 - The Unlocked Phone (G=1, G=2, R=3)
        SCORES.put("11_1_1",88.0); SCORES.put("11_2_2",84.0); SCORES.put("11_3_3",38.0);
        SCORES.put("11_1_2",86.0); SCORES.put("11_2_1",87.0); SCORES.put("11_1_3",52.0);
        SCORES.put("11_3_1",44.0); SCORES.put("11_2_3",48.0); SCORES.put("11_3_2",41.0);

        // Q13 - The Forgotten Day (Y=1, G=2, R=3)
        SCORES.put("12_1_1",64.0); SCORES.put("12_2_2",87.0); SCORES.put("12_3_3",29.0);
        SCORES.put("12_1_2",71.0); SCORES.put("12_2_1",68.0); SCORES.put("12_1_3",38.0);
        SCORES.put("12_3_1",33.0); SCORES.put("12_2_3",44.0); SCORES.put("12_3_2",26.0);

        // Q14 - The Small Lie (Y=1, G=2, R=3)
        SCORES.put("13_1_1",68.0); SCORES.put("13_2_2",89.0); SCORES.put("13_3_3",42.0);
        SCORES.put("13_1_2",74.0); SCORES.put("13_2_1",71.0); SCORES.put("13_1_3",51.0);
        SCORES.put("13_3_1",46.0); SCORES.put("13_2_3",58.0); SCORES.put("13_3_2",39.0);

        // Q15 - The Ex (G=1, G=2, R=3)
        SCORES.put("14_1_1",87.0); SCORES.put("14_2_2",83.0); SCORES.put("14_3_3",31.0);
        SCORES.put("14_1_2",85.0); SCORES.put("14_2_1",86.0); SCORES.put("14_1_3",44.0);
        SCORES.put("14_3_1",38.0); SCORES.put("14_2_3",51.0); SCORES.put("14_3_2",34.0);

        // Q16 - The New Group (Y=1, G=2, G=3)
        SCORES.put("15_1_1",71.0); SCORES.put("15_2_2",88.0); SCORES.put("15_3_3",84.0);
        SCORES.put("15_1_2",64.0); SCORES.put("15_2_1",68.0); SCORES.put("15_1_3",67.0);
        SCORES.put("15_3_1",72.0); SCORES.put("15_2_3",86.0); SCORES.put("15_3_2",82.0);

        // Q17 - The Party (R=1, G=2, Y=3)
        SCORES.put("16_1_1",42.0); SCORES.put("16_2_2",91.0); SCORES.put("16_3_3",66.0);
        SCORES.put("16_1_2",31.0); SCORES.put("16_2_1",36.0); SCORES.put("16_1_3",52.0);
        SCORES.put("16_3_1",57.0); SCORES.put("16_2_3",73.0); SCORES.put("16_3_2",61.0);

        // Q18 - The Bad Idea (G=1, Y=2, R=3)
        SCORES.put("17_1_1",87.0); SCORES.put("17_2_2",69.0); SCORES.put("17_3_3",33.0);
        SCORES.put("17_1_2",76.0); SCORES.put("17_2_1",79.0); SCORES.put("17_1_3",41.0);
        SCORES.put("17_3_1",37.0); SCORES.put("17_2_3",48.0); SCORES.put("17_3_2",29.0);

        // Q19 - The Gossip Circle (R=1, Y=2, G=3)
        SCORES.put("18_1_1",36.0); SCORES.put("18_2_2",68.0); SCORES.put("18_3_3",89.0);
        SCORES.put("18_1_2",44.0); SCORES.put("18_2_1",41.0); SCORES.put("18_1_3",28.0);
        SCORES.put("18_3_1",32.0); SCORES.put("18_2_3",76.0); SCORES.put("18_3_2",72.0);

        // Q20 - The Unexpected Speech (G=1, Y=2, G=3)
        SCORES.put("19_1_1",86.0); SCORES.put("19_2_2",67.0); SCORES.put("19_3_3",83.0);
        SCORES.put("19_1_2",74.0); SCORES.put("19_2_1",71.0); SCORES.put("19_1_3",84.0);
        SCORES.put("19_3_1",82.0); SCORES.put("19_2_3",72.0); SCORES.put("19_3_2",69.0);

        // Q21 - The Cancellation (R=1, R=2, G=3)
        SCORES.put("20_1_1",34.0); SCORES.put("20_2_2",28.0); SCORES.put("20_3_3",91.0);
        SCORES.put("20_1_2",31.0); SCORES.put("20_2_1",29.0); SCORES.put("20_1_3",62.0);
        SCORES.put("20_3_1",67.0); SCORES.put("20_2_3",58.0); SCORES.put("20_3_2",54.0);

        // Q22 - The Stress Wall (R=1, G=2, Y=3)
        SCORES.put("21_1_1",38.0); SCORES.put("21_2_2",89.0); SCORES.put("21_3_3",66.0);
        SCORES.put("21_1_2",27.0); SCORES.put("21_2_1",31.0); SCORES.put("21_1_3",49.0);
        SCORES.put("21_3_1",52.0); SCORES.put("21_2_3",74.0); SCORES.put("21_3_2",58.0);

        // Q23 - The Failure (G=1, Y=2, G=3)
        SCORES.put("22_1_1",84.0); SCORES.put("22_2_2",68.0); SCORES.put("22_3_3",81.0);
        SCORES.put("22_1_2",73.0); SCORES.put("22_2_1",76.0); SCORES.put("22_1_3",82.0);
        SCORES.put("22_3_1",83.0); SCORES.put("22_2_3",71.0); SCORES.put("22_3_2",69.0);

        // Q24 - The Support Role (G=1, G=2, G=3)
        SCORES.put("23_1_1",88.0); SCORES.put("23_2_2",86.0); SCORES.put("23_3_3",83.0);
        SCORES.put("23_1_2",87.0); SCORES.put("23_2_1",87.0); SCORES.put("23_1_3",85.0);
        SCORES.put("23_3_1",84.0); SCORES.put("23_2_3",84.0); SCORES.put("23_3_2",82.0);

        // Q25 - The Criticism (G=1, G=2, Y=3)
        SCORES.put("24_1_1",87.0); SCORES.put("24_2_2",84.0); SCORES.put("24_3_3",62.0);
        SCORES.put("24_1_2",85.0); SCORES.put("24_2_1",86.0); SCORES.put("24_1_3",71.0);
        SCORES.put("24_3_1",68.0); SCORES.put("24_2_3",73.0); SCORES.put("24_3_2",64.0);

        // Q26 - The Money Disagreement (G=1, R=2, R=3)
        SCORES.put("25_1_1",88.0); SCORES.put("25_2_2",31.0); SCORES.put("25_3_3",36.0);
        SCORES.put("25_1_2",44.0); SCORES.put("25_2_1",41.0); SCORES.put("25_1_3",48.0);
        SCORES.put("25_3_1",52.0); SCORES.put("25_2_3",33.0); SCORES.put("25_3_2",29.0);

        // Q27 - The Loan Request (G=1, G=2, R=3)
        SCORES.put("26_1_1",86.0); SCORES.put("26_2_2",83.0); SCORES.put("26_3_3",28.0);
        SCORES.put("26_1_2",84.0); SCORES.put("26_2_1",85.0); SCORES.put("26_1_3",41.0);
        SCORES.put("26_3_1",38.0); SCORES.put("26_2_3",44.0); SCORES.put("26_3_2",31.0);

        // Q28 - The Bonus (G=1, Y=2, G=3)
        SCORES.put("27_1_1",86.0); SCORES.put("27_2_2",66.0); SCORES.put("27_3_3",88.0);
        SCORES.put("27_1_2",72.0); SCORES.put("27_2_1",69.0); SCORES.put("27_1_3",87.0);
        SCORES.put("27_3_1",87.0); SCORES.put("27_2_3",74.0); SCORES.put("27_3_2",71.0);

        // Q29 - The Wasteful Spend (G=1, G=2, R=3)
        SCORES.put("28_1_1",87.0); SCORES.put("28_2_2",84.0); SCORES.put("28_3_3",32.0);
        SCORES.put("28_1_2",85.0); SCORES.put("28_2_1",86.0); SCORES.put("28_1_3",44.0);
        SCORES.put("28_3_1",41.0); SCORES.put("28_2_3",51.0); SCORES.put("28_3_2",37.0);

        // Q30 - The Broke Friend (G=1, G=2, Y=3)
        SCORES.put("29_1_1",88.0); SCORES.put("29_2_2",84.0); SCORES.put("29_3_3",67.0);
        SCORES.put("29_1_2",86.0); SCORES.put("29_2_1",87.0); SCORES.put("29_1_3",74.0);
        SCORES.put("29_3_1",71.0); SCORES.put("29_2_3",72.0); SCORES.put("29_3_2",68.0);

        // Q31 - The Dream Job (G=1, G=2, R=3)
        SCORES.put("30_1_1",84.0); SCORES.put("30_2_2",88.0); SCORES.put("30_3_3",28.0);
        SCORES.put("30_1_2",86.0); SCORES.put("30_2_1",85.0); SCORES.put("30_1_3",38.0);
        SCORES.put("30_3_1",33.0); SCORES.put("30_2_3",44.0); SCORES.put("30_3_2",31.0);

        // Q32 - The Career Gap (G=1, G=2, G=3)
        SCORES.put("31_1_1",88.0); SCORES.put("31_2_2",85.0); SCORES.put("31_3_3",89.0);
        SCORES.put("31_1_2",86.0); SCORES.put("31_2_1",87.0); SCORES.put("31_1_3",87.0);
        SCORES.put("31_3_1",88.0); SCORES.put("31_2_3",87.0); SCORES.put("31_3_2",86.0);

        // Q33 - The Big Dream (G=1, Y=2, R=3)
        SCORES.put("32_1_1",89.0); SCORES.put("32_2_2",66.0); SCORES.put("32_3_3",34.0);
        SCORES.put("32_1_2",74.0); SCORES.put("32_2_1",78.0); SCORES.put("32_1_3",42.0);
        SCORES.put("32_3_1",38.0); SCORES.put("32_2_3",51.0); SCORES.put("32_3_2",29.0);

        // Q34 - The Career Risk (G=1, G=2, Y=3)
        SCORES.put("33_1_1",87.0); SCORES.put("33_2_2",83.0); SCORES.put("33_3_3",64.0);
        SCORES.put("33_1_2",85.0); SCORES.put("33_2_1",86.0); SCORES.put("33_1_3",72.0);
        SCORES.put("33_3_1",68.0); SCORES.put("33_2_3",71.0); SCORES.put("33_3_2",66.0);

        // Q35 - The Promotion (G=1, G=2, Y=3)
        SCORES.put("34_1_1",84.0); SCORES.put("34_2_2",87.0); SCORES.put("34_3_3",62.0);
        SCORES.put("34_1_2",85.0); SCORES.put("34_2_1",85.0); SCORES.put("34_1_3",69.0);
        SCORES.put("34_3_1",66.0); SCORES.put("34_2_3",72.0); SCORES.put("34_3_2",64.0);

        // Q36 - The Family Disapproval (G=1, Y=2, R=3)
        SCORES.put("35_1_1",88.0); SCORES.put("35_2_2",67.0); SCORES.put("35_3_3",28.0);
        SCORES.put("35_1_2",74.0); SCORES.put("35_2_1",78.0); SCORES.put("35_1_3",38.0);
        SCORES.put("35_3_1",33.0); SCORES.put("35_2_3",48.0); SCORES.put("35_3_2",24.0);

        // Q37 - The Friendship Clash (G=1, G=2, G=3)
        SCORES.put("36_1_1",86.0); SCORES.put("36_2_2",88.0); SCORES.put("36_3_3",84.0);
        SCORES.put("36_1_2",87.0); SCORES.put("36_2_1",87.0); SCORES.put("36_1_3",85.0);
        SCORES.put("36_3_1",85.0); SCORES.put("36_2_3",86.0); SCORES.put("36_3_2",86.0);

        // Q38 - The Privacy Breach (G=1, G=2, R=3)
        SCORES.put("37_1_1",84.0); SCORES.put("37_2_2",89.0); SCORES.put("37_3_3",32.0);
        SCORES.put("37_1_2",86.0); SCORES.put("37_2_1",85.0); SCORES.put("37_1_3",44.0);
        SCORES.put("37_3_1",38.0); SCORES.put("37_2_3",48.0); SCORES.put("37_3_2",35.0);

        // Q39 - The Interference (R=1, G=2, Y=3)
        SCORES.put("38_1_1",34.0); SCORES.put("38_2_2",88.0); SCORES.put("38_3_3",63.0);
        SCORES.put("38_1_2",27.0); SCORES.put("38_2_1",31.0); SCORES.put("38_1_3",46.0);
        SCORES.put("38_3_1",51.0); SCORES.put("38_2_3",73.0); SCORES.put("38_3_2",57.0);

        // Q40 - The Homecoming (Y=1, G=2, R=3)
        SCORES.put("39_1_1",66.0); SCORES.put("39_2_2",87.0); SCORES.put("39_3_3",29.0);
        SCORES.put("39_1_2",74.0); SCORES.put("39_2_1",71.0); SCORES.put("39_1_3",38.0);
        SCORES.put("39_3_1",33.0); SCORES.put("39_2_3",44.0); SCORES.put("39_3_2",26.0);

        // Q41 - The Adventure (G=1, G=2, G=3)
        SCORES.put("40_1_1",87.0); SCORES.put("40_2_2",84.0); SCORES.put("40_3_3",88.0);
        SCORES.put("40_1_2",85.0); SCORES.put("40_2_1",86.0); SCORES.put("40_1_3",87.0);
        SCORES.put("40_3_1",87.0); SCORES.put("40_2_3",86.0); SCORES.put("40_3_2",85.0);

        // Q42 - The Food Battle (G=1, G=2, G=3)
        SCORES.put("41_1_1",86.0); SCORES.put("41_2_2",88.0); SCORES.put("41_3_3",87.0);
        SCORES.put("41_1_2",87.0); SCORES.put("41_2_1",87.0); SCORES.put("41_1_3",86.0);
        SCORES.put("41_3_1",86.0); SCORES.put("41_2_3",87.0); SCORES.put("41_3_2",87.0);

        // Q43 - The Lateness (R=1, G=2, Y=3)
        SCORES.put("42_1_1",32.0); SCORES.put("42_2_2",88.0); SCORES.put("42_3_3",64.0);
        SCORES.put("42_1_2",24.0); SCORES.put("42_2_1",28.0); SCORES.put("42_1_3",44.0);
        SCORES.put("42_3_1",48.0); SCORES.put("42_2_3",73.0); SCORES.put("42_3_2",56.0);

        // Q44 - The Homebody (Y=1, G=2, R=3) wait (G=1 effort, G=2 balance, R=3 stay true)
        SCORES.put("43_1_1",84.0); SCORES.put("43_2_2",88.0); SCORES.put("43_3_3",36.0);
        SCORES.put("43_1_2",86.0); SCORES.put("43_2_1",85.0); SCORES.put("43_1_3",48.0);
        SCORES.put("43_3_1",42.0); SCORES.put("43_2_3",52.0); SCORES.put("43_3_2",38.0);

        // Q45 - The Phone Problem (R=1, G=2, R=3)
        SCORES.put("44_1_1",34.0); SCORES.put("44_2_2",88.0); SCORES.put("44_3_3",29.0);
        SCORES.put("44_1_2",27.0); SCORES.put("44_2_1",31.0); SCORES.put("44_1_3",32.0);
        SCORES.put("44_3_1",28.0); SCORES.put("44_2_3",34.0); SCORES.put("44_3_2",26.0);

        // Q46 - The Flirtatious Partner (G=1, G=2, Y=3)
        SCORES.put("45_1_1",86.0); SCORES.put("45_2_2",83.0); SCORES.put("45_3_3",61.0);
        SCORES.put("45_1_2",84.0); SCORES.put("45_2_1",85.0); SCORES.put("45_1_3",71.0);
        SCORES.put("45_3_1",67.0); SCORES.put("45_2_3",69.0); SCORES.put("45_3_2",63.0);

        // Q47 - The Late Night Text (G=1, G=2, Y=3)
        SCORES.put("46_1_1",87.0); SCORES.put("46_2_2",82.0); SCORES.put("46_3_3",64.0);
        SCORES.put("46_1_2",84.0); SCORES.put("46_2_1",85.0); SCORES.put("46_1_3",73.0);
        SCORES.put("46_3_1",69.0); SCORES.put("46_2_3",71.0); SCORES.put("46_3_2",66.0);

        // Q48 - The Secret Lie (Y=1, G=2, R=3)
        SCORES.put("47_1_1",66.0); SCORES.put("47_2_2",88.0); SCORES.put("47_3_3",38.0);
        SCORES.put("47_1_2",74.0); SCORES.put("47_2_1",71.0); SCORES.put("47_1_3",48.0);
        SCORES.put("47_3_1",43.0); SCORES.put("47_2_3",54.0); SCORES.put("47_3_2",36.0);

        // Q49 - The Story Post (G=1, R=2, Y=3)
        SCORES.put("48_1_1",87.0); SCORES.put("48_2_2",28.0); SCORES.put("48_3_3",61.0);
        SCORES.put("48_1_2",38.0); SCORES.put("48_2_1",33.0); SCORES.put("48_1_3",71.0);
        SCORES.put("48_3_1",66.0); SCORES.put("48_2_3",42.0); SCORES.put("48_3_2",34.0);

        // Q50 - The Different Life Vision (R=1, G=2, Y=3)
        SCORES.put("49_1_1",32.0); SCORES.put("49_2_2",88.0); SCORES.put("49_3_3",63.0);
        SCORES.put("49_1_2",26.0); SCORES.put("49_2_1",29.0); SCORES.put("49_1_3",46.0);
        SCORES.put("49_3_1",51.0); SCORES.put("49_2_3",73.0); SCORES.put("49_3_2",57.0);
    }

    public double[] calculateScores(Integer[] girlAnswers, Integer[] boyAnswers, int[] questionIndices) {
        double[] scores = new double[10];
        for (int i = 0; i < 10; i++) {
            String key = questionIndices[i] + "_" + girlAnswers[i] + "_" + boyAnswers[i];
            scores[i] = SCORES.getOrDefault(key, 50.0);
        }
        return scores;
    }

    public double calculateFinalScore(double[] scores) {
        double total = 0;
        for (double s : scores) total += s;
        return Math.round((total / scores.length) * 10.0) / 10.0;
    }

    public String getCompatibilityLabel(double score) {
        if (score >= 85) return "Your hearts speak the same language 💕";
        if (score >= 70) return "You understand each other beautifully 🌸";
        if (score >= 55) return "You complement each other well 💗";
        if (score >= 40) return "You see things differently — and that is okay 🌷";
        return "Opposites can teach each other a lot 🤍";
    }
}
