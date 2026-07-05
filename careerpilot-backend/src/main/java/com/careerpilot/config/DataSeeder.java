package com.careerpilot.config;

import com.careerpilot.entity.*;
import com.careerpilot.repository.CodingQuestionRepository;
import com.careerpilot.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Runs once on startup and inserts seed data only when the tables are empty.
 * Idempotent — safe to restart the server multiple times.
 */
@Component
public class DataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final CompanyRepository companyRepository;
    private final CodingQuestionRepository questionRepository;

    public DataSeeder(CompanyRepository companyRepository,
                      CodingQuestionRepository questionRepository) {
        this.companyRepository  = companyRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        seedCompanies();
        seedCodingQuestions();
    }

    // ── Companies ────────────────────────────────────────────────────────────

    private void seedCompanies() {
        if (companyRepository.count() > 0) return;

        List<Company> companies = List.of(
            buildCompany("Wipro",
                "Wipro Limited is a leading global IT, consulting and business process services company.",
                "Java, Python, SQL, Communication, Problem Solving",
                "Arrays, Strings, Sorting, Searching, Basic Data Structures",
                "DBMS, OS, CN, Java OOPs, SDLC",
                "Number Series, Arithmetic, Data Interpretation, Logical Reasoning",
                "Online Test → Technical Interview → HR Interview",
                "3.5 – 6.5 LPA"),

            buildCompany("Infosys",
                "Infosys is a global leader in next-generation digital services and consulting.",
                "Java, Python, C++, Analytical Thinking, Teamwork",
                "Arrays, Linked List, Trees, Graphs, Dynamic Programming",
                "DBMS, OS, Networks, Cloud Basics, Agile",
                "Quantitative Aptitude, Verbal Ability, Logical Reasoning, Puzzles",
                "Online Aptitude → Technical Round → HR Round",
                "3.6 – 7 LPA"),

            buildCompany("TCS",
                "Tata Consultancy Services is an IT services, consulting and business solutions organisation.",
                "Java, C, Python, SQL, OOPS Concepts",
                "Strings, Arrays, Sorting, Recursion, Basic Algorithms",
                "DBMS, Operating Systems, Networking, Software Engineering",
                "TCS NQT: Numerical, Verbal, Reasoning, Coding",
                "NQT Exam → Technical Interview → Managerial → HR",
                "3.36 – 7 LPA"),

            buildCompany("Zoho",
                "Zoho Corporation builds online business, productivity and IT tools.",
                "C, C++, Java, Python, Data Structures, Problem Solving",
                "Arrays, Strings, Linked Lists, Trees, Graphs, DP, Bit Manipulation",
                "OS, DBMS, Networks, OOP, System Design Basics",
                "Basic Mathematics, Logical Reasoning, Puzzles",
                "Programming Test → Advanced Coding → Technical Interview → HR",
                "5 – 12 LPA"),

            buildCompany("Cognizant",
                "Cognizant is a leading provider of IT services, including digital, technology, consulting.",
                "Java, Python, SQL, Communication, Agile",
                "Arrays, Strings, Recursion, Sorting, Searching",
                "DBMS, OS, CN, Java, SDLC, Testing Basics",
                "Quantitative, Verbal, Logical, Analytical",
                "CCAT Test → Technical Interview → HR Interview",
                "4 – 7 LPA"),

            buildCompany("Capgemini",
                "Capgemini is a global leader in consulting, digital transformation and technology services.",
                "Java, Python, C#, SQL, Communication Skills",
                "Arrays, Strings, Basic Algorithms, Sorting",
                "DBMS, OS, Networks, OOP, Agile, DevOps Basics",
                "Pseudo Code, Game Based Assessment, Analytical",
                "Aptitude Test → Technical Interview → Behavioral Interview → HR",
                "4 – 7.5 LPA"),

            buildCompany("Accenture",
                "Accenture is a global professional services company with services in IT and consulting.",
                "Java, Python, Communication, Problem Solving, Collaboration",
                "Arrays, Strings, Searching, Sorting, Basic Data Structures",
                "DBMS, OS, Cloud Basics, SDLC, Agile",
                "Quantitative, Logical, Verbal, Attention to Detail",
                "Cognitive Assessment → Technical Interview → HR Round",
                "4.5 – 8 LPA")
        );

        companyRepository.saveAll(companies);
        log.info("Seeded {} companies", companies.size());
    }

    private Company buildCompany(String name, String desc, String skills,
                                  String coding, String technical,
                                  String aptitude, String rounds, String pkg) {
        Company c = new Company();
        c.setCompanyName(name);
        c.setDescription(desc);
        c.setRequiredSkills(skills);
        c.setCodingTopics(coding);
        c.setTechnicalTopics(technical);
        c.setAptitudeTopics(aptitude);
        c.setInterviewRounds(rounds);
        c.setPlacementPackage(pkg);
        return c;
    }

    // ── Coding Questions ─────────────────────────────────────────────────────

    private void seedCodingQuestions() {
        if (questionRepository.count() > 0) return;

        List<CodingQuestion> questions = List.of(

            // ── EASY ──
            buildQ("Two Sum",
                "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.",
                Difficulty.EASY,
                "First line: integer n (array length)\nSecond line: n space-separated integers\nThird line: integer target",
                "Two space-separated indices i and j such that nums[i] + nums[j] == target",
                "2 <= n <= 10^4, -10^9 <= nums[i] <= 10^9",
                "4\n2 7 11 15\n9", "0 1", "Arrays, Hashing"),

            buildQ("Reverse a String",
                "Write a function that reverses a string. The input string is given as an array of characters.",
                Difficulty.EASY,
                "A string of characters", "The reversed string",
                "1 <= s.length <= 10^5",
                "hello", "olleh", "Strings"),

            buildQ("FizzBuzz",
                "Given an integer n, return a string array where: answer[i] == 'FizzBuzz' if i is divisible by 3 and 5, 'Fizz' if divisible by 3, 'Buzz' if divisible by 5, else i as a string.",
                Difficulty.EASY,
                "Single integer n", "Space-separated FizzBuzz sequence",
                "1 <= n <= 10^4",
                "15", "1 2 Fizz 4 Buzz Fizz 7 8 Fizz Buzz 11 Fizz 13 14 FizzBuzz", "Strings, Math"),

            buildQ("Palindrome Check",
                "Given a string s, return true if it is a palindrome, or false otherwise.",
                Difficulty.EASY,
                "A single string", "true or false",
                "1 <= s.length <= 2*10^5",
                "racecar", "true", "Strings, Two Pointers"),

            buildQ("Find Maximum in Array",
                "Given an array of n integers, find and return the maximum element.",
                Difficulty.EASY,
                "First line: n\nSecond line: n space-separated integers", "Single integer — the maximum",
                "1 <= n <= 10^5, -10^9 <= arr[i] <= 10^9",
                "5\n3 1 4 1 5", "5", "Arrays"),

            buildQ("Count Vowels",
                "Given a string, count the number of vowels (a, e, i, o, u — case insensitive).",
                Difficulty.EASY,
                "A single string", "Integer count of vowels",
                "1 <= s.length <= 10^5",
                "Hello World", "3", "Strings"),

            // ── MEDIUM ──
            buildQ("Longest Substring Without Repeating Characters",
                "Given a string s, find the length of the longest substring without repeating characters.",
                Difficulty.MEDIUM,
                "A single string s", "Integer — length of the longest substring",
                "0 <= s.length <= 5*10^4",
                "abcabcbb", "3", "Strings, Sliding Window"),

            buildQ("Binary Search",
                "Given a sorted array of distinct integers and a target value, return the index if found, else return -1.",
                Difficulty.MEDIUM,
                "First line: n\nSecond line: n sorted integers\nThird line: target",
                "Index of target or -1",
                "1 <= n <= 10^4",
                "6\n-1 0 3 5 9 12\n9", "4", "Arrays, Binary Search"),

            buildQ("Valid Parentheses",
                "Given a string s containing just '(', ')', '{', '}', '[' and ']', determine if the input string is valid.",
                Difficulty.MEDIUM,
                "A string of bracket characters", "true or false",
                "1 <= s.length <= 10^4",
                "()[]{}", "true", "Stack"),

            buildQ("Merge Two Sorted Arrays",
                "Given two sorted arrays, merge them into one sorted array.",
                Difficulty.MEDIUM,
                "First line: m n\nSecond line: m sorted integers\nThird line: n sorted integers",
                "Single sorted array of m+n integers",
                "0 <= m, n <= 200",
                "3 3\n1 2 3\n4 5 6", "1 2 3 4 5 6", "Arrays, Two Pointers"),

            buildQ("Fibonacci Number",
                "Given n, calculate F(n) where F(0)=0, F(1)=1, F(n)=F(n-1)+F(n-2).",
                Difficulty.MEDIUM,
                "Single integer n", "F(n)",
                "0 <= n <= 30",
                "10", "55", "Recursion, Dynamic Programming"),

            buildQ("Find Duplicates in Array",
                "Given an integer array nums of length n where all integers are in range [1, n], return all integers that appear twice.",
                Difficulty.MEDIUM,
                "First line: n\nSecond line: n integers",
                "Space-separated duplicate values",
                "n == nums.length, 1 <= nums[i] <= n",
                "6\n4 3 2 7 8 2 3 1", "2 3", "Arrays, Hashing"),

            buildQ("Reverse Linked List",
                "Given the head of a singly linked list, reverse the list and return the reversed list.",
                Difficulty.MEDIUM,
                "Space-separated integers representing linked list nodes",
                "Space-separated reversed linked list",
                "0 <= number of nodes <= 5000",
                "1 2 3 4 5", "5 4 3 2 1", "Linked List"),

            // ── HARD ──
            buildQ("Longest Common Subsequence",
                "Given two strings text1 and text2, return the length of their longest common subsequence.",
                Difficulty.HARD,
                "First line: text1\nSecond line: text2",
                "Integer — length of LCS",
                "1 <= text1.length, text2.length <= 1000",
                "abcde\nace", "3", "Dynamic Programming"),

            buildQ("Word Break",
                "Given a string s and a dictionary wordDict, return true if s can be segmented into space-separated dictionary words.",
                Difficulty.HARD,
                "First line: s\nSecond line: dictionary words space-separated",
                "true or false",
                "1 <= s.length <= 300",
                "leetcode\nleet code", "true", "Dynamic Programming, Strings"),

            buildQ("Maximum Subarray (Kadane's Algorithm)",
                "Given an integer array nums, find the subarray with the largest sum and return its sum.",
                Difficulty.HARD,
                "First line: n\nSecond line: n integers",
                "Maximum subarray sum",
                "1 <= n <= 10^5, -10^4 <= nums[i] <= 10^4",
                "9\n-2 1 -3 4 -1 2 1 -5 4", "6", "Arrays, Dynamic Programming"),

            buildQ("N-Queens Problem",
                "Place n queens on an n×n chessboard so that no two queens attack each other. Return the number of distinct solutions.",
                Difficulty.HARD,
                "Single integer n", "Number of distinct solutions",
                "1 <= n <= 9",
                "4", "2", "Backtracking"),

            buildQ("Trapping Rain Water",
                "Given n non-negative integers representing an elevation map, compute how much water it can trap after raining.",
                Difficulty.HARD,
                "First line: n\nSecond line: n integers",
                "Total units of trapped water",
                "n == height.length, 0 <= n <= 3*10^4",
                "12\n0 1 0 2 1 0 1 3 2 1 2 1", "6", "Arrays, Two Pointers, Stack")
        );

        questionRepository.saveAll(questions);
        log.info("Seeded {} coding questions", questions.size());
    }

    private CodingQuestion buildQ(String title, String desc, Difficulty diff,
                                   String inputFmt, String outputFmt,
                                   String constraints, String sampleIn,
                                   String sampleOut, String topic) {
        CodingQuestion q = new CodingQuestion();
        q.setTitle(title);
        q.setDescription(desc);
        q.setDifficulty(diff);
        q.setInputFormat(inputFmt);
        q.setOutputFormat(outputFmt);
        q.setConstraints(constraints);
        q.setSampleInput(sampleIn);
        q.setSampleOutput(sampleOut);
        q.setTopic(topic);
        return q;
    }
}
