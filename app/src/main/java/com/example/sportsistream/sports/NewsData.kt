package com.example.sportsistream.sports

object NewsData {

    private const val LOREM_1 =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    private const val LOREM_2 =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    private const val LOREM_3 =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    private const val LOREM_SHORT_1 =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    private const val LOREM_SHORT_2 =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    private const val LOREM_SHORT_3 =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    private const val LOREM_SHORT_4 =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    val newsList: List<NewsItem> = listOf(
        NewsItem(
            "1", "Title-1",
            LOREM_SHORT_1,
            LOREM_1, "Football", "https://images.unsplash.com/photo-1579952363873-27f3bade9f55?w=800&h=500&fit=crop", "Apr 15, 2026", "ESPN"
        ),
        NewsItem(
            "2", "Title-2",
            LOREM_SHORT_2,
            LOREM_2, "Basketball", "https://images.unsplash.com/photo-1504450758481-7338eba7524a?w=800&h=500&fit=crop", "Apr 14, 2026", "NBA.com"
        ),
        NewsItem(
            "3", "Title-3",
            LOREM_SHORT_3,
            LOREM_3, "Cricket", "https://images.unsplash.com/photo-1531415074968-036ba1b575da?w=800&h=500&fit=crop", "Apr 14, 2026", "Cricinfo"
        ),
        NewsItem(
            "4", "Title-4",
            LOREM_SHORT_4,
            LOREM_1, "Tennis", "https://images.unsplash.com/photo-1554068865-24cecd4e34b8?w=800&h=500&fit=crop", "Apr 13, 2026", "ATP Tour"
        ),
        NewsItem(
            "5", "Title-5",
            LOREM_SHORT_1,
            LOREM_2, "Football", "https://images.unsplash.com/photo-1508098682722-e99c43a406b2?w=800&h=500&fit=crop", "Apr 13, 2026", "Sky Sports"
        ),
        NewsItem(
            "6", "Title-6",
            LOREM_SHORT_2,
            LOREM_3, "Rugby", "https://images.unsplash.com/photo-1587280501635-68a0e82cd5ff?w=800&h=500&fit=crop", "Apr 12, 2026", "World Rugby"
        ),
        NewsItem(
            "7", "Title-7",
            LOREM_SHORT_3,
            LOREM_1, "Basketball", "https://images.unsplash.com/photo-1546519638405-a4c78f3bdb3f?w=800&h=500&fit=crop", "Apr 12, 2026", "Bleacher Report"
        ),
        NewsItem(
            "8", "Title-8",
            LOREM_SHORT_4,
            LOREM_2, "Cricket", "https://images.unsplash.com/photo-1624526267942-ab0ff8a3e972?w=800&h=500&fit=crop", "Apr 11, 2026", "BBC Sport"
        )
    )

    val featuredList: List<NewsItem> = listOf(
        NewsItem(
            "f1", "News-1",
            LOREM_SHORT_1,
            LOREM_3, "Football", "https://images.unsplash.com/photo-1579952363873-27f3bade9f55?w=800&h=500&fit=crop", "Apr 15, 2026", "La Liga"
        ),
        NewsItem(
            "f2", "News-2",
            LOREM_SHORT_2,
            LOREM_1, "Cricket", "https://images.unsplash.com/photo-1624526267942-ab0ff8a3e972?w=800&h=500&fit=crop", "Apr 14, 2026", "IPL"
        ),
        NewsItem(
            "f3", "News-3",
            LOREM_SHORT_3,
            LOREM_2, "Basketball", "https://images.unsplash.com/photo-1504450758481-7338eba7524a?w=800&h=500&fit=crop", "Apr 13, 2026", "NBA"
        ),
        NewsItem(
            "f4", "News-4",
            LOREM_SHORT_4,
            LOREM_3, "Tennis", "https://images.unsplash.com/photo-1622163642998-1ea32b0bbc67?w=800&h=500&fit=crop", "Apr 12, 2026", "ATP Tour"
        )
    )

    val categories: List<String> = listOf("All", "Football", "Cricket","Basketball", "Rugby", "Tennis")
}
