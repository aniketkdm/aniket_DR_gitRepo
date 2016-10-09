#! /bin/bash

## Declare and build the hashmap / declarative array

declare -A truck1k_hm

while read line
do
	id=`echo $line | cut -d"," -f1 | sed -e 's/^t_//' -e 's/_30.out//'`
	#echo $id
	truck1k_hm[$id]="t_${id}_28_29.out"
	#echo "${!truck1k_hm[$id]} ${truck1k_hm[$id]}"
done < /home/aniket/NEW_DATASET_URBAN_BACKUP/SummerSteps/Step1_2/1000TrucksSelectedOutput/truck_max_dist/top_1000_long_distance_trucks.out

#fl_nm=`[ ${truck1k_hm["P9XS1FB4GU"]} ] &&  echo ${truck1k_hm["P9XS1FB4GU"]} || echo "not exists"`

#echo $fl_nm

file_28_wc=`wc -l /home/aniket/NEW_DATASET_URBAN_BACKUP/formatted_STRING_20140328.txt | cut -d" " -f1`

#for i in $(seq 1 $file_28_wc)
#for i in $(seq 1 2)
#do
#	sed -n ${i}p /home/aniket/NEW_DATASET_URBAN_BACKUP/formatted_STRING_20140328.txt
#done

#i=1

### WHILE LOOP READING MUCH FASTER THAN SED PRINTING

while read line_28
do
	#echo $line_28
	id=`echo $line_28 | cut -d"," -f1`
	echo $id
	fl_nm=`[ ${truck1k_hm["$id"]} ] &&  echo ${truck1k_hm["$id"]} || echo "not exists"`
	echo $fl_nm
	if [ "$fl_nm" != "not exists" ]
	then
		echo $line_28 >> "/home/aniket/NEW_DATASET_URBAN_BACKUP/Fall_16/Step1_all_28_29_records_for1000Trucks/hashmap_output/$fl_nm"
	fi
	#i=`expr $i + 1`
	#if [ $i -eq 3 ]
	#then
	#	break
	#fi
done < /home/aniket/NEW_DATASET_URBAN_BACKUP/formatted_STRING_20140328.txt
