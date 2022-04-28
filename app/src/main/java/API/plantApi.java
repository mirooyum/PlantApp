package API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface plantApi {
    @GET("Plantdb/search.php")
    Call<List<plantData>> getData(@Query("TemperatureMinimum") double TemperatureMinimum,
                                  @Query("moisture") String moisture,
                                  @Query("Shade") String Shade,
                                  @Query("PH") String ph,
                                  @Query("season") String season,
                                  @Query("State") String State);

    @GET("Plantdb/read_single.php")
    Call<List<plantData>> getData(@Query("CommonName") String CommonName);

    @GET("Plantdb/singlePlant.php")
    Call<plantData> getplantData(@Query("CommonName") String CommonName);

    @GET("Plantdb/singlePlantID.php")
    Call<plantData> getBETYData(@Query("betydbspeciesid") int betydbspeciesid);

    @GET("Plantdb/searchAdv.php")
    Call<List<plantData>> getadvData(@Query("TemperatureMinimum") double TemperatureMinimum,
                                     @Query("moisture") String moisture,
                                     @Query("Shade") String Shade,
                                     @Query("PH") String ph,
                                     @Query("GrowthHabit") String GrowthHabit,
                                     @Query("season") String season,
                                     @Query("BloomPeriod") String BloomPeriod,
                                     @Query("State") String State,
                                     @Query("CommercialAvailability") String CommercialAvailability,
                                     @Query("DroughtTolerance")String DroughtTolerance,
                                     @Query("PalatableHuman")String PalatableHuman);

    @GET("Plantdb/searchPlants.php")
    Call<List<plantData>> getsearchPlants(@Query("CommonName") String CommonName,
                                          @Query("GrowthHabit") String GrowthHabit,
                                          @Query("season") String season,
                                          @Query("BloomPeriod") String BloomPeriod,
                                          @Query("State") String State,
                                          @Query("CommercialAvailability") String CommercialAvailability,
                                          @Query("DroughtTolerance")String DroughtTolerance,
                                          @Query("PalatableHuman")String PalatableHuman);


    @GET("runs/read.php")
    Call<List<runsData>> getrunsData();

    @GET("results/searchRun.php")
    Call<List<resultsData>> getresultsData(@Query("runID")int runID);


}