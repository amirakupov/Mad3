import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.HomeScreen
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.screens.WatchlistScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Detail.route) {
            //DetailScreen(navController = navController)
                backStackEntry->
            val movieId = backStackEntry.arguments?.getString("movieId")
            DetailScreen(navController, movieId)

            //DetailScreen(movieId = backStackEntry.arguments?.getString("movieId"))
        }
        composable(Screen.Watchlist.route){
            WatchlistScreen(navController)
        }
    }
}