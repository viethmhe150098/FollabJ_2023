import React from "react";
import "./Cards.css";
import {
  UilClipboardAlt,
} from "@iconscout/react-unicons";

import Card from "../Card/Card";
import { useSelector } from "react-redux";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { getProjectStatistics } from "../../../../Redux/project/projectActions";
import { useState } from "react";


const Cards = () => {
  const dispatch = useDispatch()

  //const projectStatistic = useSelector((state) => state.project.statistics)

  // const [page_number, setPageNumber] = useState(0)

  function getDaysInMonth() {
    var date = new Date();
    date.setDate(1);
    var currentMonth = date.getMonth()
    var days = [];

    while (date.getMonth() === currentMonth) {
      days.push(date.getDate());
      date.setDate(date.getDate() + 1);
    }
    return days;
  }

  function getProjectNumberPerDay(projectStatistic) {
    const numberArray = []
    //console.log(projectStatistic.projectsPerDay)
    for (let i = 1; i <= getDaysInMonth().length; i++) {
      numberArray.push(0)
      // if (projectStatistic.projectsPerDay.length === 0) 
      // {
      //   return 0;
      // }
      // else 
      // {
        projectStatistic.projectsPerDay.some((number) =>
        {
          // condition of the callback function.
          if (parseInt(number.countBy) === i) {
            numberArray.pop()
            numberArray.push(number.count)
            return true;
          }
        });
      // }
    }
    return numberArray;
  }

  function getProjectNumberPerMonth(projectStatistic) {
    const numberArray = []
    //console.log(projectStatistic.projectsPerDay)
    for (let i = 1; i <= 12; i++) {
      numberArray.push(0)
      projectStatistic.projectsPerMonth.some((number) => {
        // condition of the callback function.
        if (parseInt(number.countBy) === i) {
          numberArray.pop()
          numberArray.push(number.count)
          return true;
        }
      });
    }
    return numberArray;
  }

  function getProjectNumberPerYear(projectStatistic) {
    const numberArray = []
    const date = new Date()
    //console.log(projectStatistic.projectsPerDay)
    for (let i = 2019; i <= date.getYear() + 1900; i++) {
      numberArray.push(0)
      projectStatistic.projectsPerYear.some((number) => {
        // condition of the callback function.
        if (parseInt(number.countBy) === i) {
          numberArray.pop();
          numberArray.push(number.count);
          return true;
        }
      });
    }
    return numberArray;
  }

  const [cardsData, setData] = useState([])

  useEffect(() => {
    dispatch(getProjectStatistics()).unwrap().then((result) => {
      setData([
        {
          title: "New project in day",
          color: {
            backGround: "linear-gradient(180deg, #bb67ff 0%, #c484f3 100%)",
            boxShadow: "0px 10px 20px 0px #e0c6f5",
          },
          barValue: 20,
          value: result.by_day,
          png: UilClipboardAlt,
          series: [
            {
              name: "New project per day",
              data: getProjectNumberPerDay(result),
            },
          ],
          categories: getDaysInMonth()
        },
        {
          title: "New project in month",
          color: {
            backGround: "linear-gradient(180deg, #FF919D 0%, #FC929D 100%)",
            boxShadow: "0px 10px 20px 0px #FDC0C7",
          },
          barValue: 40,
          value: result.by_month,
          png: UilClipboardAlt,
          series: [
            {
              name: "New project per month",
              data: getProjectNumberPerMonth(result),
            },
          ],
          categories: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        },
        {
          title: "New project in year",
          color: {
            backGround:
              "linear-gradient(rgb(248, 212, 154) -146.42%, rgb(255 202 113) -46.42%)",
            boxShadow: "0px 10px 20px 0px #F9D59B",
          },
          barValue: 60,
          value: result.by_year,
          png: UilClipboardAlt,
          series: [
            {
              name: "New project per year",
              data: getProjectNumberPerYear(result),
            },
          ],
          categories: [2019, 2020, 2021, 2022, 2023]
        },
      ]);
    }) 
    
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])


  // useEffect(() => {
  //   setData([
  //       {
  //         title: "New project in day",
  //         color: {
  //           backGround: "linear-gradient(180deg, #bb67ff 0%, #c484f3 100%)",
  //           boxShadow: "0px 10px 20px 0px #e0c6f5",
  //         },
  //         barValue: 20,
  //         value: projectStatistic.by_day,
  //         png: UilClipboardAlt,
  //         series: [
  //           {
  //             name: "New project per day",
  //             data: getProjectNumberPerDay(),
  //           },
  //         ],
  //         categories: getDaysInMonth()
  //       },
  //       {
  //         title: "New project in month",
  //         color: {
  //           backGround: "linear-gradient(180deg, #FF919D 0%, #FC929D 100%)",
  //           boxShadow: "0px 10px 20px 0px #FDC0C7",
  //         },
  //         barValue: 40,
  //         value: projectStatistic.by_month,
  //         png: UilClipboardAlt,
  //         series: [
  //           {
  //             name: "New project per month",
  //             data: getProjectNumberPerMonth(),
  //           },
  //         ],
  //         categories: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
  //       },
  //       {
  //         title: "New project in year",
  //         color: {
  //           backGround:
  //             "linear-gradient(rgb(248, 212, 154) -146.42%, rgb(255 202 113) -46.42%)",
  //           boxShadow: "0px 10px 20px 0px #F9D59B",
  //         },
  //         barValue: 60,
  //         value: projectStatistic.by_year,
  //         png: UilClipboardAlt,
  //         series: [
  //           {
  //             name: "New project per year",
  //             data: getProjectNumberPerYear(),
  //           },
  //         ],
  //         categories: [2019, 2020, 2021, 2022, 2023]
  //       },
  //     ]);
  //     // eslint-disable-next-line react-hooks/exhaustive-deps
  // }, [projectStatistic])

  return (
    <div className="Cards">
      {cardsData != null && cardsData.map((card, id) => {
        return (
          <div className="parentContainer" key={id}>
            <Card
              title={card.title}
              color={card.color}
              barValue={card.barValue}
              value={card.value}
              png={card.png}
              series={card.series}
              categories={card.categories}
            />
          </div>
        );
      })}

    </div>
  );
};

export default Cards;
