for i in $(seq 1 1000)
do

        line=`head -$i /home/aniket/NEW_DATASET_URBAN_BACKUP/SummerSteps/Step1_2/1000TrucksSelectedOutput/truck_max_dist/top_1000_long_distance_trucks.out | tail -1`
        id=`echo $line | cut -d"," -f1 | sed -e 's/^t_//' -e 's/_30.out//'`

	cat /home/aniket/NEW_DATASET_URBAN_BACKUP/Fall_16/Step1_all_28_29_records_for1000Trucks/merged_28_29_1000Truck_records/t_${id}* /home/aniket/NEW_DATASET_URBAN_BACKUP/SummerSteps/Step3/1000TrucksAllData/All30_31/t_${id}* > /home/aniket/NEW_DATASET_URBAN_BACKUP/Fall_16/Step2_merge_28_29_30_31_files/output_files/t_${id}_28_29_30_31.out

done
