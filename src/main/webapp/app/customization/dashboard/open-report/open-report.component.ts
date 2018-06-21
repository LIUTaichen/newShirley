import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Niggle } from '../../../entities/niggle';
import * as moment from 'moment';
import { NiggleUtilService } from '../../niggle-util.service';
import { NiggleSnapshotService } from '../../../entities/niggle-snapshot/niggle-snapshot.service';
import { NiggleSnapshot } from '../../../entities/niggle-snapshot';
import { MatTableDataSource, MatSort } from '@angular/material';

@Component({
  selector: 'jhi-open-report',
  templateUrl: './open-report.component.html',
  styleUrls: ['./open-report.component.css']
})
export class OpenReportComponent implements OnInit {
  @Input() niggles: Niggle[];
  displayedColumns = ['date', 'total', 'closed', 'opened', 'ageOfOldest'];
  dataSource: MatTableDataSource<Element> = new MatTableDataSource([]);
  rows: Element[] = new Array<Element>();
  weeks: Date[] = new Array<Date>();
  @ViewChild(MatSort) sort: MatSort;

  constructor(private niggleUtilService: NiggleUtilService,
    private niggleSnapshotService: NiggleSnapshotService) { }

  ngOnInit() {
    const dateOfEndOfCurrentWeek = this.niggleUtilService.getDateOfEndOfCurrentWeek();
    const momentOfEndOfCurrentWeek = moment(dateOfEndOfCurrentWeek);
    for (let i = 0; i < 4; i++) {
      const m = moment(momentOfEndOfCurrentWeek);
      m.add(-1 * i, 'weeks');
      console.log(m);
      this.weeks.push(m.toDate());
    }
    console.log(this.weeks);
    this.weeks.map((week) => {
      const weektime = week.getTime();
      const endtime = dateOfEndOfCurrentWeek.getTime();
      if (weektime === endtime) {
        this.addRowToDataSource(this.generateRowForCurrentWeek(week));
      } else {
        const momentOfSnapshotDate = moment(week).add(1, 'days');
        const date = momentOfSnapshotDate.format('YYYY-MM-DD');
        this.niggleSnapshotService.query({
          'date.equals': date
        }).subscribe((snapshots) => {
          const row: Element = this.convertToRow(snapshots.body);
          let closed = 0;
          let opened = 0;
          const startOfWeek = moment(week).add(-1, 'weeks').toDate();
          this.niggles.map((niggle) => {
            if (niggle.dateClosed && niggle.dateClosed > startOfWeek && niggle.dateClosed < week) {
              closed++;
            }
            if (niggle.dateOpened && niggle.dateOpened > startOfWeek && niggle.dateOpened < week) {
              opened++;
            }
          }, this);
          row.date = week;
          row.opened = opened;
          row.closed = closed;
          this.addRowToDataSource(row);
        });
      }
    });
  }

  addRowToDataSource(row: Element) {
    this.rows.push(row);
    this.dataSource = new MatTableDataSource(this.rows);
    this.dataSource.sort = this.sort;
  }

  convertToRow(snapshots: NiggleSnapshot[]): Element {
    const row: Element = {
      date: new Date(),
      total: -1,
      closed: -1,
      opened: -1,
      ageOfOldest: -1
    };
    if (snapshots && snapshots.length > 0) {
      let totalOpen = 0;
      let age = 0;
      snapshots.map((snapshot) => {
        if (snapshot.status.toString() === 'OPEN' || snapshot.status.toString() === 'IN_PROGRESS' || snapshot.status.toString() === 'ON_HOLD') {
          totalOpen += snapshot.count;
          if (snapshot.ageOfOldest > age) {
            age = snapshot.ageOfOldest;
          }
        }
      });
      row.ageOfOldest = age;
      row.total = totalOpen;
    }
    return row;
  }

  generateRowForCurrentWeek(week: Date): Element {
    let totalOpen = 0;
    let closed = 0;
    let opened = 0;
    let dateOpenedOfOldsest = new Date();
    const startOfWeek = moment(week).add(-1, 'weeks').toDate();
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
      if (this.niggleUtilService.isCountedAsOpen(niggle) && niggle.dateOpened < dateOpenedOfOldsest
      ) {
        dateOpenedOfOldsest = niggle.dateOpened;
      }
    });
    const daysOpened = moment().diff(moment(dateOpenedOfOldsest), 'days');
    return {
      date: week,
      total: totalOpen,
      closed,
      opened,
      ageOfOldest: daysOpened
    };
  }
}
export interface Element {
  date: Date;
  total: number;
  closed: number;
  opened: number;
  ageOfOldest: number;
}
