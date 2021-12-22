if [ -z "$(git status --porcelain)" ]; then 

echo cd FRC-2022
cd FRC-2022
echo rm -r frc
rm -r frc
echo cp -r ../src/main/java/frc frc
cp -r ../src/main/java/frc frc
echo rm -r ../docs
rm -r ../docs
echo javadoc -subpackages edu:frc:org:pabeles -d ../docs
javadoc -subpackages edu:frc:org:pabeles -d ../docs
echo "echo theme: jekyll-theme-tactile > ../docs/_config.yml" 
echo theme: jekyll-theme-tactile > ../docs/_config.yml
echo git commit -a -m "Updated Javadoc"
git commit -a -m "Updated Javadoc"
git push

else 

echo Git Directory Not Clean

fi