import React from "react";
import "./Cards.css";
import {
  UilClipboardAlt,
  UilUsdSquare,
  UilMoneyWithdrawal,
} from "@iconscout/react-unicons";

import Card from "../Card/Card";


const Cards = () => {
  const cardsData = [
  {
    title: "New project per day",
    color: {
      backGround: "linear-gradient(180deg, #bb67ff 0%, #c484f3 100%)",
      boxShadow: "0px 10px 20px 0px #e0c6f5",
    },
    barValue: 70,
    value: "25,970",
    png: UilUsdSquare,
    series: [
      {
        name: "New project per day",
        data: [31, 40, 28, 51, 42, 109, 100],
      },
    ],
  },
  {
    title: "New project per day",
    color: {
      backGround: "linear-gradient(180deg, #FF919D 0%, #FC929D 100%)",
      boxShadow: "0px 10px 20px 0px #FDC0C7",
    },
    barValue: 80,
    value: "14,270",
    png: UilMoneyWithdrawal,
    series: [
      {
        name: "New project per day",
        data: [10, 100, 50, 70, 80, 30, 40],
      },
    ],
  },
  {
    title: "New project per year",
    color: {
      backGround:
        "linear-gradient(rgb(248, 212, 154) -146.42%, rgb(255 202 113) -46.42%)",
      boxShadow: "0px 10px 20px 0px #F9D59B",
    },
    barValue: 60,
    value: "4,270",
    png: UilClipboardAlt,
    series: [
      {
        name: "New project per year",
        data: [10, 25, 15, 30, 12, 15, 20],
      },
    ],
  },
];
  return (
    <div className="Cards">
      {cardsData.map((card, id) => {
        return (
          <div className="parentContainer" key={id}>
            <Card
              title={card.title}
              color={card.color}
              barValue={card.barValue}
              value={card.value}
              png={card.png}
              series={card.series}
            />
          </div>
        );
      })}

    </div>
  );
};

export default Cards;
