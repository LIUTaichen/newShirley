import { Injectable } from '@angular/core';
import { Niggle } from '../entities/niggle';
import * as moment from 'moment';

@Injectable()
export class NiggleUtilService {

  constructor() { }

  test() {
    console.log('test success');
  }

  isOverDue(niggle: Niggle): Boolean {
    if (!niggle) {
      return false;
    }
    if (!niggle.priority || !niggle.dateOpened) {
      return false;
    }
    if (niggle.status.toString() !== 'OPEN') {
      return false;
    }
    const currentMoment = moment(new Date());
    const openedMoment = moment(niggle.dateOpened);
    const daysOpened = currentMoment.diff(openedMoment, 'days');
    if (niggle.priority.toString() === 'HIGH' && daysOpened >= 7) {
      return true;
    }
    if (niggle.priority.toString() === 'MEDIUM' && daysOpened >= 14) {
      return true;
    }
    if (niggle.priority.toString() === 'LOW' && daysOpened >= 28) {
      return true;
    }
    return false;
  }

  getDateOfEndOfCurrentWeek(): Date {
    const currentMoment = moment(new Date());
    let daysToAdd = 2 - currentMoment.weekday();
    if (daysToAdd < 0) {
      // add 7 days if current date falls on tuesday to Saturday
      daysToAdd += 7;
    }
    const dateOfNextTuesday = currentMoment.add(daysToAdd, 'days');
    const dateOfBeginingOfNextTuesday = dateOfNextTuesday.startOf('day').toDate();
    return dateOfBeginingOfNextTuesday;
  }

}
