import { Component, OnInit, Input } from '@angular/core';
import { Niggle } from '../../../entities/niggle';
import * as moment from 'moment';
import { NiggleUtilService } from '../../niggle-util.service';

@Component({
  selector: 'jhi-open-report',
  templateUrl: './open-report.component.html',
  styleUrls: ['./open-report.component.css']
})
export class OpenReportComponent implements OnInit {

  @Input() niggles: Niggle[];
  displayedColumns = ['weekending', 'total', 'closed', 'opened', 'ageOfOldest'];
  dataSource = new Array<Element>();
  weeks: Date[] = new Array<Date>();

  constructor(private niggleUtilService: NiggleUtilService) { }

  ngOnInit() {
    const dateOfEndOfCurrentWeek = this.niggleUtilService.getDateOfEndOfCurrentWeek();
    const momentOfEndOfCurrentWeek = moment(dateOfEndOfCurrentWeek);
    for (let i = 0; i < 4; i++) {
      this.weeks.push(momentOfEndOfCurrentWeek.add(-1 * i, 'weeks').toDate());
    }
    this.weeks.map((week) => {
      let totalOpen = 0;
      let closed = 0;
      let opened = 0;
      let dateOpenedOfOldsest = new Date();
      const startOfWeek = moment(week).add(-1, 'weeks').toDate();
      let old: Niggle;
      this.niggles.map((niggle) => {
        if (this.niggleUtilService.isOpenAtDate(niggle, week)) {
          totalOpen++;
        }
        if (niggle.dateClosed && niggle.dateClosed > startOfWeek && niggle.dateClosed < week) {
          closed++;
        }

        if (niggle.dateOpened && niggle.dateOpened > startOfWeek && niggle.dateOpened < week) {
          opened++;
        }

        if (niggle.dateOpened
          && (!niggle.dateClosed || niggle.dateClosed > week)
          && niggle.dateOpened < dateOpenedOfOldsest
         ) {
          dateOpenedOfOldsest = niggle.dateOpened;
          old = niggle;
        }
      }, this);

      const daysOpened = moment(week).diff(moment(dateOpenedOfOldsest), 'days');
      console.log(week);
      console.log(old);
      this.dataSource.push({
        weekending: week,
        total: totalOpen,
        closed,
        opened,
        ageOfOldest: daysOpened
      });
    }, this);

  }

}
export interface Element {
  weekending: Date;
  total: number;
  closed: number;
  opened: number;
  ageOfOldest: number;
}

const ELEMENT_DATA: Element[] = [
  { weekending: new Date('2018-06-11'), total: 189, closed: 40, opened: 20, ageOfOldest: 215 },
  { weekending: new Date('2018-06-04'), total: 189, closed: 40, opened: 20, ageOfOldest: 215 },
  { weekending: new Date('2018-05-28'), total: 189, closed: 40, opened: 20, ageOfOldest: 215 },
];
