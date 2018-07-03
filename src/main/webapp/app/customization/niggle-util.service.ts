import { Injectable } from '@angular/core';
import { Niggle, Priority } from '../entities/niggle';
import * as moment from 'moment';
import { NiggleRow } from './niggle-dw/niggle-list-dw/niggle-row.model';
import { Plant } from '../entities/plant';
import { Project } from '../entities/project';

@Injectable()
export class NiggleUtilService {

  public static convertEntityToRow(niggle: Niggle): NiggleRow {
    const niggleDaysOpened = NiggleUtilService.getDaysOpened(niggle);
    let fleetId, plantDesctiption, plantCategory, siteAndName, location, locationUpdateTime, owner, contractor, orderNo, googleLink;
    if (niggle.plant) {
      const plant: Plant = niggle.plant;
      fleetId = plant.fleetId;
      plantCategory = plant.category ? plant.category['category'] : '';
      plantDesctiption = plant.description;
      siteAndName = plant.project ? plant.project['jobNumber'] + ' ' + plant.project['name'] : '';
      owner = plant.owner ? plant.owner['company'] : '';

      if (plant.location) {
        location = plant.location['address'];
        if (plant.location['project']) {
          const project: Project = plant.location['project'];
          location = project.jobNo + ' - ' + project.name;
        }
        locationUpdateTime = plant.location['timestamp'];
        if (plant.location['latitude'] && plant.location['longitude']) {
          googleLink = 'https://www.google.com/maps/search/?api=1&query=' + plant.location['latitude'] + ',' + plant.location['longitude'];
        }

      }
    }
    contractor = niggle.assignedContractor ? niggle.assignedContractor['name'] : '';
    orderNo = niggle.purchaseOrder ? niggle.purchaseOrder['orderNumber'] : '';

    const priorityOrder: any = Priority[niggle.priority];
    const niggleRow: NiggleRow = {
      id: niggle.id,
      description: niggle.description,
      orderNo,
      status: niggle.status,
      note: niggle.note,
      priority: niggle.priority,
      priorityOrder,
      quattraReference: niggle.quattraReference,
      quattraComments: niggle.quattraComments,
      dateOpened: niggle.dateOpened,
      dateCompleted: niggle.dateCompleted,
      dateUpdated: niggle.lastModifiedDate,
      dateClosed: niggle.dateClosed,
      plantNumber: fleetId,
      plantDescription: plantDesctiption,
      plantCategory,
      site: siteAndName,
      location,
      locationUpdateTime,
      owner,
      googleLink,
      eta: niggle.eta,
      contractor,
      daysOpened: niggleDaysOpened,
      createdBy: niggle.createdBy,
      createdDate: niggle.createdDate,
      lastModifiedBy: niggle.lastModifiedBy,
      lastModifiedDate: niggle.lastModifiedDate
    };
    return niggleRow;
  }

  public static getDaysOpened(niggle: Niggle) {
    if (niggle.dateOpened) {
      return Math.floor(Math.abs(niggle.dateOpened.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
    } else {
      return null;
    }
  }

  constructor() { }

  isOverDue(niggle: Niggle): Boolean {
    if (!niggle) {
      return false;
    }
    if (!niggle.priority || !niggle.dateOpened) {
      return false;
    }
    if (niggle.status.toString() !== 'OPEN' && niggle.status.toString() !== 'IN_PROGRESS' && niggle.status.toString() !== 'ON_HOLD') {
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
    let daysToAdd = 1 - currentMoment.weekday();
    if (daysToAdd < 0) {
      // add 7 days if current date falls on tuesday to Saturday
      daysToAdd += 7;
    }
    const dateOfNextTuesday = currentMoment.add(daysToAdd, 'days');
    const dateOfBeginingOfNextTuesday = dateOfNextTuesday.startOf('day').toDate();
    return dateOfBeginingOfNextTuesday;
  }

  isOpenAtDate(niggle: Niggle, date: Date) {
    if (!niggle.dateOpened) {
      return false;
    }
    if (!niggle.dateClosed) {
      return true;
    }

    if (niggle.dateOpened < date && niggle.dateClosed > date) {
      return true;
    } else {
      return false;
    }
  }

  isCountedAsOpen(niggle: Niggle) {
    if (!niggle.dateOpened) {
      return false;
    }
    if (niggle.status.toString() === 'OPEN' || niggle.status.toString() === 'IN_PROGRESS' || niggle.status.toString() === 'ON_HOLD') {
      return true;
    }
  }

}
