# ShowMeTheMoney

---
### <Git 사용법>
```
# 0. 초기 세팅
## cd 명령어를 통해 깃을 저장할 폴더로 이동하고 깃을 복사한다.
git clone 레포주소

# 1. main 브렌치의 최신 커밋에서 자신의 브렌치를 만들고 체크아웃한다.
## main 브렌치에 체크아웃
git switch main
## 깃헙 원격 저장소에서 main 브렌치의 최신 버전을 가져옴. (다른 사람이 main 브렌치에 새로운 커밋을 머지했을 수 있으니)
git pull origin main
## 0908_naheun 브렌치를 만들면서 체크아웃
git switch -c 0908_naheun

# 2. 작업한다.

# 3. 작업이 끝나면 커밋 후 main 브렌치에 Merge한다.
## 작업했던 모든 파일들(변경사항)을 새로운 커밋(버전)의 대상으로 만들어준다.
git add .
## 새로운 커밋(버전)을 만든다. (커밋한다.)
git commit -m "적절한 설명을 꼭 적어주세요."

### 작업 중이던 브렌치를 깃허브에 반영(푸쉬)한다.
git push origin 0908_naheun
### github의 레포지토리 페이지에서 Pull Requests 섹션에 들어간다.
### New Pull Requests 버튼을 누르고, Base를 main으로 Compare를 0908_naheun 브렌치로 설정한다.
### 적절한 제목 및 본문을 추가하고 Create Pull Requests 버튼을 누른다.
### 이후 적절히 리뷰 등을 받고 해당 페이지에서 머지할 수 있다.

# 4. 작업한 브렌치는 삭제한다.
## 깃허브 페이지에서 머지한 후 delete branch.
### VScode 사용자라면 Git Graph를 누른 후 구름 모양 버튼을 누른다.
### main/origin 버튼을 더블클릭해서 없앤다.
### 0908_naheun 브렌치를 우클릭하여 delete 한다.