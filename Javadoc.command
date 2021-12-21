echo cd "$(dirname "$0")"
cd "$(dirname "$0")"
echo cd "$(dirname "$0")"/build/libs/FRC-2022
cd build/libs/FRC-2022
echo rm -r frc
rm -r frc
echo cp -r ../../../src/main/java/frc frc
cp -r ../../../src/main/java/frc frc
echo rm -r ../../../docs
rm -r ../../../docs
echo javadoc -subpackages frc -d ../../../docs
javadoc -subpackages frc -d ../../../docs
read -p "Press any key to close" -n 1