count=0

while read line
do
        id=`echo $line | cut -d"," -f1 | sed -e 's/t_//' -e 's/_30.out//'`

        java FindStableLocationsTabularMultipleDays /home/aniket/NEW_DATASET_URBAN_BACKUP/Fall_16/Step2_merge_28_29_30_31_files/output_files/t_${id}_28_29_30_31.out /home/aniket/NEW_DATASET_URBAN_BACKUP/Fall_16/Step3_RunJava_FindStableLocs_TagLocs/out_t_${id}_28_29_30_31.out

        count=`expr $count + 1`
        echo "$count: $id: done"

done < /home/aniket/NEW_DATASET_URBAN_BACKUP/SummerSteps/Step1_2/1000TrucksSelectedOutput/truck_max_dist/top_1000_long_distance_trucks.out


