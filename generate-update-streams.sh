#!/bin/bash

set -eu

rm -rf social_network/
rm -f datagen.log

for SF in 0.1; do
    echo "=> SF: ${SF}" | tee -a datagen.log

    for NUMPART in 1 2 4 8 16 32 64 128 256 512; do
        echo "--> NUMPART: ${NUMPART}" | tee -a datagen.log

        echo > params.ini
        echo ldbc.snb.datagen.generator.scaleFactor:snb.interactive.${SF} >> params.ini
        echo ldbc.snb.datagen.serializer.numUpdatePartitions:${NUMPART} >> params.ini
        echo ldbc.snb.datagen.serializer.dynamicActivitySerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.activity.CsvBasicDynamicActivitySerializer >> params.ini
        echo ldbc.snb.datagen.serializer.dynamicPersonSerializer:ldbc.snb.datagen.serializer.snb.csv.dynamicserializer.person.CsvBasicDynamicPersonSerializer >> params.ini
        echo ldbc.snb.datagen.serializer.staticSerializer:ldbc.snb.datagen.serializer.snb.csv.staticserializer.CsvBasicStaticSerializer >> params.ini

        ./run.sh
        cp params.ini social_network/
        # drop data files, we only need the update streams
        rm -rf social_network/{static,dynamic}
        mv social_network/ social_network-sf${SF}-numpart-${NUMPART}/
    done

done
